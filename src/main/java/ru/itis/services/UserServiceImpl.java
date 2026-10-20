package ru.itis.services;

import ru.itis.dto.AjaxDto;
import ru.itis.modules.User;
import ru.itis.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() throws SQLException {
        return userRepository.findAll();
    }

    @Override
    public void addUser(AjaxDto form) throws SQLException {
        User user = User.builder()
                .name(form.getFirstName())
                .surname(form.getLastName())
                .email(form.getEmail())
                .password("qwerty007")
                .build();
        userRepository.save(user);
    }

    @Override
    public void addUser(User user) throws SQLException {
        userRepository.save(user);
    }

    @Override
    public List<User> findByCity(String city) throws SQLException {
        return userRepository.findByCity(city);
    }

    @Override
    public List<User> findByRole(String role) throws SQLException {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> findByPhone(String phone) throws SQLException {
        return userRepository.findByPhone(phone);
    }
}