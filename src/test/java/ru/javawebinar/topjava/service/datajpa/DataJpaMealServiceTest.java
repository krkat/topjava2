package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getWithUser() {
        Meal actual = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        MEAL_MATCHER.assertMatch(actual, adminMeal1);
        USER_MATCHER.assertMatch(actual.getUser(), admin);
    }

    @Test
    public void getWithNotOwnUser() {
        assertThrows(NotFoundException.class, () -> service.getWithUser(ADMIN_MEAL_ID, USER_ID));
    }

    @Test
    public void getWithUserNotFound() {
        Assert.assertThrows(NotFoundException.class,
                () -> service.getWithUser(MealTestData.NOT_FOUND, ADMIN_ID));
    }
}
