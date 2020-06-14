package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Enter users servlet");

        String selectedUserSt = request.getParameter("userId");
        if (selectedUserSt != null) {
            SecurityUtil.setAuthUserId(Integer.parseInt(selectedUserSt));
            log.debug("New user id =" + Integer.toString(SecurityUtil.authUserId()));
            response.sendRedirect("index.html");
            return;
        }

        log.debug("forward to users");
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
