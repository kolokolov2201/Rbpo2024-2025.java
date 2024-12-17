package ru.mtuci.rbpo_2024_praktika.service;

import ru.mtuci.rbpo_2024_praktika.model.ApplicationUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<ApplicationUser> findById(Long id);
    void deleteUser(Long id);
    List<ApplicationUser> getAllUsers();
}