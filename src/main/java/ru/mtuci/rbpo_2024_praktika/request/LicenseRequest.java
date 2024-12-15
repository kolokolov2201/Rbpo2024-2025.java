package ru.mtuci.rbpo_2024_praktika.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Owner ID is required")
    private Long ownerId;

    @NotNull(message = "License Type ID is required")
    private Long licenseTypeId;

    @Min(value = 0, message = "Device count must be a non-negative number")
    private Integer deviceCount;

    private String description;
}