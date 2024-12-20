package ru.mtuci.rbpo_2024_praktika.controller;

import org.springframework.web.bind.annotation.*;
import ru.mtuci.rbpo_2024_praktika.model.ApplicationUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.mtuci.rbpo_2024_praktika.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final UserService userService;
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/view")
    public ResponseEntity<List<ApplicationUser>> getAllUsers() {
        try {
            List<ApplicationUser> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}