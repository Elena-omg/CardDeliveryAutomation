package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    // Метод для генерации даты: берем текущую и прибавляем shift дней
    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    // Метод для выбора города из списка (так надежнее, чем просто Faker)
    public static String generateCity(Faker faker) {
        var cities = new String[]{"Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург", "Нижний Новгород", "Казань", "Челябинск", "Омск", "Самара"};
        return cities[new Random().nextInt(cities.length)];
    }

    // Метод для генерации Имени и Фамилии через Faker
    public static String generateName(Faker faker) {
        // Используем lastName + firstName и заменяем 'ё' на 'е' для стабильности формы
        String name = faker.name().lastName() + " " + faker.name().firstName();
        return name.replace("ё", "е");
    }

    // Метод для генерации телефона
    public static String generatePhone(Faker faker) {
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private static Faker faker;

        private Registration() {
        }

        // Основной метод, который создает готового пользователя
        public static UserInfo generateUser(String locale) {
            faker = new Faker(new Locale(locale));
            return new UserInfo(generateCity(faker), generateName(faker), generatePhone(faker));
        }
    }

    // Тот самый класс данных с аннотацией Lombok
    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}