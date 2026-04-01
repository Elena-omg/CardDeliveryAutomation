package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;

class CardDeliveryTest {

    @BeforeEach
    void setup() {
        // Открываем страницу перед каждым тестом
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should replan meeting")
    void shouldReplanMeeting() {
        // 1. Подготовка данных через наш генератор
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        // 2. Первичное планирование встречи
        $("[data-test-id=city] input").setValue(validUser.getCity());
        // Очистка поля даты (стандартный способ для таких инпутов)
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(Condition.exactText("Запланировать")).click();

        // Проверка сообщения об успехе
        $("[data-test-id=success-notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate));

        // 3. Перепланирование на другую дату
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $$("button").find(Condition.exactText("Запланировать")).click();

        // Проверка появления окна перепланирования
        $("[data-test-id=replan-notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));

        // Нажимаем кнопку "Перепланировать" во всплывающем окне
        $("[data-test-id=replan-notification] button").click();

        // 4. Финальная проверка сообщения об успехе с новой датой
        $("[data-test-id=success-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate));
    }
}