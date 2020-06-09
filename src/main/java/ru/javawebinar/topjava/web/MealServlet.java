package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMemoryImpl;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealServlet extends HttpServlet {
    private final MealDao dao = MealDaoMemoryImpl.getInstance();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealTo> meals = MealsUtil.convertToTransferObjects(dao.getAll(), MealsUtil.getCaloriesPerDay());
        request.setAttribute("mealsList", meals);
        request.setAttribute("formatter", formatter);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
