package ru.javawebinar.topjava.to;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Objects;

public class MealTo extends BaseTo {

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    @ConstructorProperties({"id", "dateTime", "description", "calories", "excess"})
    public MealTo(Integer id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MealTo)) return false;
        MealTo mealTo = (MealTo) o;
        return getCalories() == mealTo.getCalories() &&
                isExcess() == mealTo.isExcess() &&
                getId().equals(mealTo.getId()) &&
                getDateTime().equals(mealTo.getDateTime()) &&
                getDescription().equals(mealTo.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDateTime(), getDescription(), getCalories(), isExcess());
    }
}
