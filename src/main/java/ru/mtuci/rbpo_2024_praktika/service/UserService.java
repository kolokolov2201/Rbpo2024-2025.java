package ru.mtuci.rbpo_2024_praktika.service;

import ru.mtuci.rbpo_2024_praktika.model.ApplicationUser;
import java.util.Optional;

public interface UserService {
    Optional<ApplicationUser> findById(Long id); // Long instead of UUID
}