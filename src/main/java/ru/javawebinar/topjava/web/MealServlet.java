package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final String MEALS = "/meals.jsp";
    private static final String ADD = "/createMeal.jsp";
    private static final String UPDATE = "/editMeal.jsp";
    private MealRepository mealRepository;

    @Override
    public void init() throws ServletException {
        mealRepository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "listMeal";
        }
        String forward = MEALS;
        if (action.equalsIgnoreCase("update")) {
            forward = UPDATE;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = mealRepository.get(mealId);
            request.setAttribute("meal", meal);
            log.info("Updating form for meal with id {}", meal.getId());
        } else if (action.equalsIgnoreCase("insert")) {
            forward = ADD;
            request.setAttribute("meal", new Meal());
            log.info("Getting add meal form");
        } else {
            forward = MEALS;
            Collection<Meal> meals = mealRepository.getAll();
            Collection<MealTo> tos = MealsUtil.getTos(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY);
            request.setAttribute("tos", tos);
            log.info("Getting meals list");
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("delete".equalsIgnoreCase(action)) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealRepository.delete(mealId);
            log.info("Meal with id {} was deleted", mealId);
        } else {
            LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("dateTime"));
            String description = request.getParameter("description");
            int calories = Integer.parseInt(request.getParameter("calories"));
            Meal meal;
            if (action.equalsIgnoreCase("insert")) {
                meal = new Meal(localDateTime, description, calories);
                mealRepository.save(meal);
                log.info("Added meal: {}", meal.getDescription());
            } else if (action.equalsIgnoreCase("update")) {
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                Meal updatedMeal = new Meal(mealId, localDateTime, description, calories);
                mealRepository.save(updatedMeal);
                log.info("Updated meal with Id {}", updatedMeal.getId());
            }
        }
        response.sendRedirect(request.getContextPath() + "/meals");
    }
}