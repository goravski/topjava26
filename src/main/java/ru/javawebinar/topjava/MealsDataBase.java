package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealsDataBase {
    private static Integer idMeal =0;
    private List<Meal> mealsDataBase = new CopyOnWriteArrayList<>();

    public List<Meal> getMealsDataBase() {
        return mealsDataBase;
    }

    public void addToMealList (Meal meal){
        idMeal= idMeal+1;
        mealsDataBase.add(new Meal(idMeal, meal.getDateTime(), meal.getDescription(), meal.getCalories()));
    }
    public boolean  deleteFromMealList (Meal meal){
        return mealsDataBase.remove(meal);
    }
    }

