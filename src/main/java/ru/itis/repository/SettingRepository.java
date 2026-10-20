package ru.itis.repository;

import java.sql.SQLException;

public interface SettingRepository {
    int getUsersToAdd() throws SQLException;
}
