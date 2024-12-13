package ru.mtuci.rbpo_2024_praktika.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private Long Lifetime;
    private Date firstActivationDate;
    private Date endingDate;
    private Long userId;
    private String deviceId;
    private String blocked;
    private String digitalSignature;
    @JsonIgnore
    private License license;
    @JsonIgnore
    private Device device;
}

