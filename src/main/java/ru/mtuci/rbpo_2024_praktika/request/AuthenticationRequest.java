package ru.mtuci.rbpo_2024_praktika.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.mtuci.rbpo_2024_praktika.model.ApplicationRole;

@Data
@AllArgsConstructor
public class AuthenticationRequest {

    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private ApplicationRole role;
    private String username;
}
