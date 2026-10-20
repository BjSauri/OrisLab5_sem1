package ru.itis.servlets;

import ru.itis.modules.User;
import ru.itis.services.SettingService;
import ru.itis.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/save")
public class SaveUsersServlet extends HttpServlet {

    private SettingService settingService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        settingService = (SettingService) config.getServletContext().getAttribute("settingService");
        userService = (UserService) config.getServletContext().getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int count = settingService.getUsersToAdd();
            req.setAttribute("count", count);
            req.getRequestDispatcher("/WEB-INF/saveForm.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Ошибка при получении количества", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int count = settingService.getUsersToAdd();
            List<User> users = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                User user = User.builder()
                        .name(req.getParameter("firstName" + i))
                        .surname(req.getParameter("lastName" + i))
                        .age(parseIntOrNull(req.getParameter("age" + i)))
                        .city(req.getParameter("city" + i))
                        .role(req.getParameter("role" + i))
                        .phone(req.getParameter("phone" + i))
                        .build();
                users.add(user);
            }


            for (User user : users) {
                userService.addUser(user);
            }


            resp.sendRedirect(req.getContextPath() + "/success.html");

        } catch (SQLException e) {
            throw new ServletException("Ошибка при сохранении", e);
        }
    }

    private Integer parseIntOrNull(String val) {
        if (val == null || val.trim().isEmpty()) return null;
        return Integer.parseInt(val);
    }
}
