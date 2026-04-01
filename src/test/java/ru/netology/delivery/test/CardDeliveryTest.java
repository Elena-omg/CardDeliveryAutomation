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
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should replan meeting")
    void shouldReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);


        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(Condition.exactText("Запланировать")).click();

        $("[data-test-id=success-notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate));


        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $$("button").find(Condition.exactText("Запланировать")).click();

        $("[data-test-id=replan-notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));



        $("[data-test-id='replan-notification'] .button").shouldBe(Condition.visible).click();
        
        $("[data-test-id=success-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate));
    }
}