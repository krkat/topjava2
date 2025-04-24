package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MatcherFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.user;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + "/";

    @Autowired
    private MealService mealService;

    @Test
    void getAll() throws Exception {
        MatcherFactory.Matcher<MealTo> matcher = MatcherFactory.usingIgnoringFieldsComparator(MealTo.class);
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(matcher.contentJson(MealsUtil.getTos(meals, user.getCaloriesPerDay())));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MEAL_MATCHER.contentJson(meal1));
    }

    @Test
    void createWithLocation() throws Exception {
        Meal newMeal = getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andExpect(status().isCreated());

        Meal created = MEAL_MATCHER.readFromJson(actions);
        int id = created.id();
        newMeal.setId(id);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(mealService.get(id, USER_ID), newMeal);
    }

    @Test
    void update() throws Exception {
        Meal updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        MEAL_MATCHER.assertMatch(mealService.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    void getBetween() throws Exception {
        MatcherFactory.Matcher<MealTo> matcher = MatcherFactory.usingIgnoringFieldsComparator(MealTo.class);
        perform(MockMvcRequestBuilders.get(REST_URL +
                "filter?startDate=" + "2020-01-30" +
                "&startTime=" + "10:00:00" +
                "&endDate=" + "2020-01-30" +
                "&endTime=" + "19:00:00"
        ))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(matcher.contentJson(MealsUtil.getTos(List.of(meal2, meal1), user.getCaloriesPerDay())));
    }

    @Test
    void getBetweenDates() throws Exception {
        MatcherFactory.Matcher<MealTo> matcher = MatcherFactory.usingIgnoringFieldsComparator(MealTo.class);
        perform(MockMvcRequestBuilders.get(REST_URL +
                "filter?startDate=" + "2020-01-30" +
                "&startTime=" +
                "&endDate=" + "2020-01-30" +
                "&endTime="
        ))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(matcher.contentJson(MealsUtil.getTos(List.of(meal3, meal2, meal1), user.getCaloriesPerDay())));
    }

    @Test
    void getBetweenTimes() throws Exception {
        MatcherFactory.Matcher<MealTo> matcher = MatcherFactory.usingIgnoringFieldsComparator(MealTo.class);
        perform(MockMvcRequestBuilders.get(REST_URL +
                "filter?startDate=" +
                "&startTime=" + "10:00:00" +
                "&endDate=" +
                "&endTime=" + "19:00:00"
        ))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(matcher.contentJson(
                        MealsUtil.getFilteredTos(
                                meals,
                                user.getCaloriesPerDay(),
                                LocalTime.parse("10:00:00"),
                                LocalTime.parse("19:00:00"))));
    }
}