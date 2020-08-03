package ru.javawebinar.topjava.web;

import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.web.SecurityUtil.*;

class RootControllerTest extends AbstractControllerTest {
    private static int userId;

    @BeforeAll
    static void beforeAll() {
        userId = authUserId();
    }

    @AfterAll
    static void afterAll() {
        setAuthUserId(userId);
    }

    @Test
    void getUsers() throws Exception {
        perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users",
                        new AssertionMatcher<List<User>>() {
                            @Override
                            public void assertion(List<User> actual) throws AssertionError {
                                USER_MATCHER.assertMatch(actual, ADMIN, USER);
                            }
                        }
                ));
    }

    private void getMealHelper(List<Meal> meals) throws Exception {
        perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", MealsUtil.getTos(meals, authUserCaloriesPerDay())));
    }

    @Test
    void getMealsForUser() throws Exception {
        setAuthUserId(USER_ID);
        getMealHelper(MEALS);
    }

    @Test
    void getMealsForAdmin() throws Exception {
        setAuthUserId(ADMIN_ID);
        getMealHelper(ADMIN_MEALS);
    }
}