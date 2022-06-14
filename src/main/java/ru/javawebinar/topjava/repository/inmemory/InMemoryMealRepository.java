package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.MealServlet;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        if (repository.get(id).getIdUser() == ID_USER) {
            log.info("meal deleted");
            return repository.remove(id) != null;
        }
        log.info("meal not deleted cause it's empty or don't own user id = " + ID_USER);
        return false;
    }

    @Override
    public Meal get(int id) {
        if (repository.get(id).getIdUser() == ID_USER) {
            return repository.get(id);
        }
        return new Meal(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values()
                .stream()
                .filter(meal -> meal.getIdUser() == ID_USER)
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList());
    }
}

