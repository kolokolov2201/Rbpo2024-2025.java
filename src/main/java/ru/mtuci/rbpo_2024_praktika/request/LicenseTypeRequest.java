package ru.mtuci.rbpo_2024_praktika.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseTypeRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Default duration is required")
    @Positive(message = "Default duration must be a positive number")
    private Integer defaultDuration;

    private String description;
}