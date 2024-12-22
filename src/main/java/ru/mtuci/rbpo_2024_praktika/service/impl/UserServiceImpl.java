package ru.mtuci.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.mtuci.rbpo_2024_praktika.model.ApplicationUser;
import ru.mtuci.rbpo_2024_praktika.repository.UserRepository;
import ru.mtuci.rbpo_2024_praktika.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<ApplicationUser> findById(Long id) { 
        return userRepository.findById(id);
    }
    @Override
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)){
            throw new IllegalArgumentException("User not found for id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<ApplicationUser> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public ApplicationUser getAuthenticatedUser(UserDetails userDetails) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setEmail(userDetails.getUsername());
        return applicationUser;
    }
}