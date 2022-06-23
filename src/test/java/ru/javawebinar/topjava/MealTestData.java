package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;


public class MealTestData {
    public static final int MEAL_ID = 100006;

    public static final Meal NEW_MEAL = new Meal(
            100006,
            LocalDateTime.of(2020, 01, 31, 0, 0),
            "Еда на граничное значение", 100
    );

public  static Meal getUpdatedMeal (){
    Meal updated = new Meal(NEW_MEAL);
    updated.setCalories(200);
    updated.setDescription("Ужин");
    return updated;
}
}
