package ru.itis.servlets;

import ru.itis.modules.User;
import ru.itis.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/search.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String field = req.getParameter("field");
        String value = req.getParameter("value");
        List<User> users = null;

        try {
            switch (field) {
                case "city":
                    users = userService.findByCity(value);
                    break;
                case "role":
                    users = userService.findByRole(value);
                    break;
                case "phone":
                    users = userService.findByPhone(value);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Ошибка поиска", e);
        }

        System.out.println("SELECT WHERE " + field + " = '" + value + "' ");
        if (users != null && !users.isEmpty()) {
            for (User u : users) {
                System.out.println("ID: " + u.getId() + " | " + u.getName() + " " + u.getSurname() + " | " + u.getCity() + " | " + u.getRole() + " | " + u.getPhone());
            }
        } else {
            System.out.println("Ничего не найдено.");
        }

        req.setAttribute("users", users);
        req.setAttribute("field", field);
        req.setAttribute("value", value);
        req.getRequestDispatcher("/WEB-INF/searchResult.jsp").forward(req, resp);
    }
}
