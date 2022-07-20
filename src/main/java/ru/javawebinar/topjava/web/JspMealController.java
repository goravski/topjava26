package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService mealService;

    @GetMapping("/index")
    public String root() {
        log.info("index");
        return "index";
    }

    @PostMapping("/meals")
    public String setMeal(HttpServletRequest request) {
        int userId = getId(request);
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.hasLength(request.getParameter("id"))) {
            mealService.update(meal, userId);
            log.info("updateMeal {}", meal);
        } else {
            mealService.create(meal, userId);
            log.info("createMeal {}", meal);
        }
        return "redirect:meals";
    }

    @GetMapping("/meals")
    public String getMeal(HttpServletRequest request, Model model) {
        String action = request.getParameter("action");
        int userId = SecurityUtil.authUserId();
        switch (action == null ? "all" : action) {
            case "delete" -> {
                int id = getId(request);
                mealService.delete(id, userId);
                return "redirect:meals";
            }
            case "create", "update" -> {
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealService.get(getId(request), userId);
                model.addAttribute("meal", meal);
                return "mealForm";
            }
            case "filter" -> {
                LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
                LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
                LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
                LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
                model.addAttribute("meals", mealService.getBetweenInclusive(startDate, endDate, userId));
                return "meals";
            }
            default -> {
                model.addAttribute("meals",
                        MealsUtil.getTos(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
                return "meals";
            }
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
