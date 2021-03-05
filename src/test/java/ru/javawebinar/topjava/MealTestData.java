package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

public class MealTestData {

     public final static Integer MEAL_ID1 = 100000;

     public final static Integer MEAL_ID2 = 100001;

     public final static Meal MEAL_TEST1 = new Meal(MEAL_ID1, LocalDateTime.of(2020, Month.JANUARY, 1, 10, 0), "Перекус", 500);

     public final static Meal MEAL_TEST2 = new Meal(MEAL_ID2, LocalDateTime.of(2020, Month.FEBRUARY, 2, 13, 0), "Укус", 1000);

}
