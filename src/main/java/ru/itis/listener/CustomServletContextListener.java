package ru.itis.listener;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.repository.UserRepository;
import ru.itis.repository.UserRepository;
import ru.itis.repository.UserRepositoryJdbcImpl;
import ru.itis.repository.UserRepositoryJdbcImpl;
import ru.itis.services.*;
import ru.itis.repository.SettingRepository;
import ru.itis.repository.SettingRepositoryJdbcImpl;
import ru.itis.services.SettingService;
import ru.itis.services.SettingServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

@WebListener
public class CustomServletContextListener implements ServletContextListener {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/testdb_11_503";
    private static final String DB_DRIVER = "org.postgresql.Driver";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);

        UserRepository userRepository = new UserRepositoryJdbcImpl(dataSource);
        SignUpService signUpService = new SignUpServiceImpl(userRepository);
        UserService usersService = new UserServiceImpl(userRepository);

        servletContext.setAttribute("userRepository", userRepository);
        servletContext.setAttribute("signUpService", signUpService);
        servletContext.setAttribute("usersService", usersService);

        SettingRepository settingRepository =
                new SettingRepositoryJdbcImpl(dataSource);

        SettingService settingService =
                new SettingServiceImpl(settingRepository);

        servletContext.setAttribute("settingService", settingService);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

