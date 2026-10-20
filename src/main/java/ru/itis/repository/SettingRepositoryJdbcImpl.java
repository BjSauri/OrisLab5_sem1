package ru.itis.repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingRepositoryJdbcImpl implements SettingRepository {

    private final DataSource dataSource;

    private static final String SQL_SELECT_COUNT =
            "SELECT \"value\" FROM settings WHERE \"key\" = ?";

    public SettingRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int getUsersToAdd() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_SELECT_COUNT)) {

            statement.setString(1, "users_to_add");

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException(
                            "В settings нет настройки users_to_add"
                    );
                }

                int count = resultSet.getInt("value");

                if (resultSet.wasNull() || count < 1 || count > 50) {
                    throw new SQLException(
                            "users_to_add должно быть числом от 1 до 50"
                    );
                }

                if (resultSet.next()) {
                    throw new SQLException(
                            "В settings несколько записей users_to_add"
                    );
                }

                return count;
            }
        }
    }
}
