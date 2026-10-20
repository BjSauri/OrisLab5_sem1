package ru.itis.repository;

import ru.itis.modules.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository extends CrudRepository<User> {
    List<User> findAll() throws SQLException;
    List<User> findByCity(String city) throws SQLException;
    List<User> findByRole(String role) throws SQLException;
    List<User> findByPhone(String phone) throws SQLException;
}
