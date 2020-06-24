package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

public class MealTestData {
    public static final int MEAL_ID = ADMIN_ID + 3;
    public static final int NOT_FOUND = 5;

    public static final Meal USER_MEAL1 = new Meal(ADMIN_ID + 1, LocalDateTime.of(2020, 1, 29, 11, 0), "Предыдущий день", 2100);
    public static final Meal USER_MEAL2 = new Meal(ADMIN_ID + 2, LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL3 = new Meal(ADMIN_ID + 3, LocalDateTime.of(2020, 1, 30, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL4 = new Meal(ADMIN_ID + 4, LocalDateTime.of(2020, 1, 30, 20, 0), "Ужин", 500);
    public static final Meal USER_MEAL5 = new Meal(ADMIN_ID + 5, LocalDateTime.of(2020, 1, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal USER_MEAL6 = new Meal(ADMIN_ID + 6, LocalDateTime.of(2020, 1, 31, 10, 0), "Завтрак", 1000);
    public static final Meal USER_MEAL7 = new Meal(ADMIN_ID + 7, LocalDateTime.of(2020, 1, 31, 13, 0), "Обед", 500);
    public static final Meal USER_MEAL8 = new Meal(ADMIN_ID + 8, LocalDateTime.of(2020, 1, 31, 20, 0), "Ужин", 410);
    public static final Meal USER_MEAL9 = new Meal(ADMIN_ID + 9, LocalDateTime.of(2020, 2, 1, 20, 0), "Следующий день", 699);

    public static final Meal ADMIN_MEAL1 = new Meal(ADMIN_ID + 10, LocalDateTime.of(2020, 1, 25, 9, 35), "Плотный завтрак", 900);
    public static final Meal ADMIN_MEAL2 = new Meal(ADMIN_ID + 11, LocalDateTime.of(2020, 1, 25, 10, 0), "Перекус", 120);
    public static final Meal ADMIN_MEAL3 = new Meal(ADMIN_ID + 12, LocalDateTime.of(2020, 1, 24, 13, 30), "Обед", 600);
    public static final Meal ADMIN_MEAL4 = new Meal(ADMIN_ID + 13, LocalDateTime.of(2020, 1, 23, 19, 0), "Ужин в ресторане", 1200);

    public static final Meal MEAL = USER_MEAL3;

    public static final Meal[] ALL_MEALS_OF_USER_SORTED = {USER_MEAL9, USER_MEAL8, USER_MEAL7, USER_MEAL6, USER_MEAL5, USER_MEAL4, USER_MEAL3, USER_MEAL2, USER_MEAL1};
    public static final Meal[] MEALS_OF_USER_BETWEEN_30_31_INCLUSIVE_SORTED = {USER_MEAL8, USER_MEAL7, USER_MEAL6, USER_MEAL5, USER_MEAL4, USER_MEAL3, USER_MEAL2};

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2027, 4, 12, 9, 35), "Новое описание", 999);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL);
        updated.setDescription("Макароны");
        updated.setCalories(350);
        updated.setDateTime(LocalDateTime.of(2011, 4, 25, 2, 52));
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
