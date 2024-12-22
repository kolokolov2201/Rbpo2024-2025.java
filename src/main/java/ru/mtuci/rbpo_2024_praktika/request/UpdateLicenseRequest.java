package ru.mtuci.rbpo_2024_praktika.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdateLicenseRequest {
    private String code;
    private String macAddress;
}
