package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;

import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/mealServiceTestDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {

    @Autowired
    private MealService mealService;

    @Test
    public void testGet() {
        Meal mealActual = mealService.get(MealTestData.MEAL_ID1, UserTestData.USER_ID);
        assertThat(mealActual).
                usingRecursiveComparison().isEqualTo(MealTestData.MEAL_TEST1);

        assertThrows(NotFoundException.class, () -> mealService.get(MealTestData.MEAL_ID1, 2));
    }

    @Test
    public void testDelete() {
        mealService.delete(MealTestData.MEAL_ID1, UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(MealTestData.MEAL_ID1, USER_ID));
    }

    @Test
    public void testUpdate() {
        Meal meal = MealTestData.MEAL_TEST1;
        meal.setCalories(5000);
        meal.setDescription("updated");
        mealService.update(meal, USER_ID);
        assertThat(mealService.get(MealTestData.MEAL_ID1, USER_ID)).usingRecursiveComparison()
                .isEqualTo(meal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                mealService.create(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 1, 10, 0), "аукс", 200), MealTestData.MEAL_ID1));
    }
}