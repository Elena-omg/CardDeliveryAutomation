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

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        var cities = new String[]{
                "Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург",
                "Нижний Новгород", "Казань", "Челябинск", "Омск", "Самара",
                "Ростов-на-Дону", "Уфа", "Красноярск", "Воронеж", "Пермь", "Волгоград"
        };
        return cities[new Random().nextInt(cities.length)];
    }

    public static String generateName(Faker faker) {
        String name = faker.name().lastName() + " " + faker.name().firstName();
        return name.replace("ё", "е");
    }

    public static String generatePhone(Faker faker) {
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private static Faker faker;

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            faker = new Faker(new Locale(locale));
            return new UserInfo(generateCity(), generateName(faker), generatePhone(faker));
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}