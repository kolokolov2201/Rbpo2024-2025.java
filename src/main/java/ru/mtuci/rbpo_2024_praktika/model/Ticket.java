package ru.mtuci.rbpo_2024_praktika.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mtuci.rbpo_2024_praktika.model.Device;
import ru.mtuci.rbpo_2024_praktika.model.License;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
public class Ticket {

    private Date serverDate;
    private Long ticketLifetime;
    private Date first_activation_date;
    private Date ending_date;
    private Long userId;
    private String deviceId;
    private String blocked;
    private String digital_signature;
    @JsonIgnore
    private License license;
    @JsonIgnore
    private Device device;
}

