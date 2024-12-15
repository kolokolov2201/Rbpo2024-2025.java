package ru.mtuci.rbpo_2024_praktika.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRequest {

    @NotBlank(message = "Device name is required")
    private String name;

    private String macAddress;

    @NotNull(message = "User ID is required")
    private Long userId;
}