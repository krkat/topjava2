package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 3;
    public static final int MEAL2_ID = START_SEQ + 4;
    public static final int MEAL3_ID = START_SEQ + 5;
    public static final int MEAL4_ID = START_SEQ + 6;
    public static final int MEAL5_ID = START_SEQ + 7;
    public static final int ADMIN_MEAL1_ID = START_SEQ + 8;
    public static final int ADMIN_MEAL2_ID = START_SEQ + 9;

    public static final int NOT_FOUND = 10;

    public static Meal user_meal_1 = new Meal(MEAL1_ID, LocalDateTime.of(2025, 1, 18, 7, 0, 0), "Завтрак", 500);
    public static Meal user_meal_2 = new Meal(MEAL2_ID, LocalDateTime.of(2025, 1, 18, 10, 0, 0), "Обед", 1000);
    public static Meal user_meal_3 = new Meal(MEAL3_ID, LocalDateTime.of(2025, 1, 18, 17, 0, 0), "Ужин", 501);
    public static Meal user_meal_4 = new Meal(MEAL4_ID, LocalDateTime.of(2025, 1, 19, 7, 0, 0), "Завтрак", 500);
    public static Meal user_meal_5 = new Meal(MEAL5_ID, LocalDateTime.of(2025, 1, 19, 10, 0, 0), "Обед", 1000);

    public static Meal admin_meal_1 = new Meal(ADMIN_MEAL1_ID, LocalDateTime.of(2025, 1, 18, 7, 0, 0), "Завтрак", 600);
    public static Meal admin_meal_5 = new Meal(ADMIN_MEAL2_ID, LocalDateTime.of(2025, 1, 18, 12, 0, 0), "Перекус", 100);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2025, 1, 20, 7, 0, 0), "Новая еда", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(user_meal_1);
        updated.setDateTime(LocalDateTime.of(2025, 1, 20, 7, 0, 0));
        updated.setDescription("Измененное описание");
        updated.setCalories(200);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }
}
