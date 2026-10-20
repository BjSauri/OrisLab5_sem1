package ru.itis.services;

import java.sql.SQLException;

public interface SettingService {
    int getUsersToAdd() throws SQLException;
}
