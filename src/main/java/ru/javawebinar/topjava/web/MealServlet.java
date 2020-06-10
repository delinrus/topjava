package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealServlet extends HttpServlet {
    private final MealDao dao = MealDaoMemoryImpl.getInstance();
    private DateTimeFormatter formatter;
    private static final String INSERT_OR_EDIT = "/mealEditForm.jsp";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String dateTimeFormat = config.getInitParameter("dateTimeFormat");
        formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        String idString = request.getParameter("mealId");
        long mealId = (idString == null || idString.isEmpty()) ? 0L : Long.parseLong(request.getParameter("mealId"));
        Meal meal = new Meal(mealId, dateTime, description, calories);

        if (mealId == 0L) {
            dao.create(meal);
        } else {
            dao.update(meal);
        }

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = (action == null) ? "" : action;

        switch (action) {
            case "edit":
                long mealId = Long.parseLong(request.getParameter("mealId"));
                Meal meal = dao.get(mealId);
                request.setAttribute("meal", meal);
                RequestDispatcher view = request.getRequestDispatcher(INSERT_OR_EDIT);
                view.forward(request, response);
                return;
            case "add":
                view = request.getRequestDispatcher(INSERT_OR_EDIT);
                meal = new Meal(null, null, null, 0);
                request.setAttribute("meal", meal);
                view.forward(request, response);
                return;
            case "delete":
                mealId = Long.parseLong(request.getParameter("mealId"));
                dao.delete(mealId);
            default:
                List<MealTo> meals = MealsUtil.convertToTransferObjects(dao.getAll(), MealsUtil.CALORIES_PER_DAY);
                request.setAttribute("mealsList", meals);
                request.setAttribute("formatter", formatter);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }
}
