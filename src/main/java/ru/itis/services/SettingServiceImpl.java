package ru.itis.services;

import ru.itis.repository.SettingRepository;

import java.sql.SQLException;

public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;

    public SettingServiceImpl(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public int getUsersToAdd() throws SQLException {
        return settingRepository.getUsersToAdd();
    }
}
