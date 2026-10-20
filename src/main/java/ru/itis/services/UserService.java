package ru.itis.services;

import ru.itis.dto.AjaxDto;
import ru.itis.modules.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    List<User> getAll() throws SQLException;
    void addUser(AjaxDto form) throws SQLException;
    void addUser(User user) throws SQLException;
    List<User> findByCity(String city) throws SQLException;
    List<User> findByRole(String role) throws SQLException;
    List<User> findByPhone(String phone) throws SQLException;
}
