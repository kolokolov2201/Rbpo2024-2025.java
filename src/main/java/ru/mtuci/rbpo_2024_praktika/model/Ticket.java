package ru.mtuci.rbpo_2024_praktika.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class Ticket {

    private Date serverDate;
    private Long lifetime;
    private Date firstActivationDate;
    private Date endingDate;
    private Long userId;
    private Long deviceId;
    private String blocked;
    private String digitalSignature;
    @JsonIgnore
    private License license;
    @JsonIgnore
    private Device device;
}