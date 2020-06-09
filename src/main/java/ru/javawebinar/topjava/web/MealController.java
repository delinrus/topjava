package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealController extends HttpServlet {
    private final MealDao dao = MealDaoMemoryImpl.getInstance();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final static String INSERT_OR_EDIT = "/mealEditForm.jsp";
    private final static String LIST_MEAL = "meals";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            long mealId = Long.parseLong(request.getParameter("mealId"));
            dao.delete(mealId);
            response.sendRedirect(LIST_MEAL);
        } else if (action.equalsIgnoreCase("listUser")) {
            response.sendRedirect(LIST_MEAL);
        } else if (action.equalsIgnoreCase("edit")) {
            long mealId = Long.parseLong(request.getParameter("mealId"));
            MealTo meal = MealsUtil.createTo(dao.get(mealId), false);
            request.setAttribute("meal", meal);
            RequestDispatcher view = request.getRequestDispatcher(INSERT_OR_EDIT);
            view.forward(request, response);
        } else if (action.equalsIgnoreCase("add")) {
            RequestDispatcher view = request.getRequestDispatcher(INSERT_OR_EDIT);
            MealTo meal = new MealTo(null, null, null, 0, false);
            request.setAttribute("meal", meal);
            view.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        String idString = request.getParameter("mealId");
        Long mealId = (idString == null || idString.isEmpty()) ? 0L : Long.parseLong(request.getParameter("mealId"));
        Meal meal = new Meal(mealId, dateTime, description, calories);

        if (idString == null || idString.isEmpty()) {
            dao.create(meal);
        } else {
            dao.update(meal);
        }

        response.sendRedirect("meals");
    }
}
