package ru.itis.repository;

import ru.itis.modules.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryJdbcImpl implements UserRepository {

    private final DataSource dataSource;

    private static final String SQL_INSERT =
            "INSERT INTO example " +
                    "(first_name, last_name, age, city, role, phone) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_ALL = "SELECT id, first_name, last_name, age, city, role, phone " + "FROM example";

    private static final String SQL_SELECT_BY_CITY = SQL_SELECT_ALL + " WHERE city = ?";
    private static final String SQL_SELECT_BY_ROLE = SQL_SELECT_ALL + " WHERE role = ?";
    private static final String SQL_SELECT_BY_PHONE = SQL_SELECT_ALL + " WHERE phone = ?";

    public UserRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(User user) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_INSERT)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());

            if (user.getAge() == null) {
                statement.setNull(3, Types.INTEGER);
            } else {
                statement.setInt(3, user.getAge());
            }

            statement.setString(4, user.getCity());
            statement.setString(5, user.getRole());
            statement.setString(6, user.getPhone());

            statement.executeUpdate();
        }
    }

    @Override
    public List<User> findAll() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            return readUsers(resultSet);
        }
    }

    @Override
    public List<User> findByCity(String city) throws SQLException {
        return findByParameter(SQL_SELECT_BY_CITY, city);
    }

    @Override
    public List<User> findByRole(String role) throws SQLException {
        return findByParameter(SQL_SELECT_BY_ROLE, role);
    }

    @Override
    public List<User> findByPhone(String phone) throws SQLException {
        return findByParameter(SQL_SELECT_BY_PHONE, phone);
    }

    private List<User> findByParameter(String sql, String value)
            throws SQLException {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, value);

            try (ResultSet resultSet = statement.executeQuery()) {
                return readUsers(resultSet);
            }
        }
    }

    private List<User> readUsers(ResultSet resultSet)
            throws SQLException {

        List<User> users = new ArrayList<>();

        while (resultSet.next()) {
            Integer age = resultSet.getObject("age", Integer.class);

            User user = User.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("first_name"))
                    .surname(resultSet.getString("last_name"))
                    .age(age)
                    .city(resultSet.getString("city"))
                    .role(resultSet.getString("role"))
                    .phone(resultSet.getString("phone"))
                    .build();

            users.add(user);
        }

        return users;
    }
}
