package ru.mtuci.rbpo_2024_praktika.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationRequest {

    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private ApplicationRole role;
    private String username;
}
