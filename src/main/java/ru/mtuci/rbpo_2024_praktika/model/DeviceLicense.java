package ru.mtuci.rbpo_2024_praktika.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "device_license")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_id", nullable = false)
    private Long licenseId;

    @Column(name = "device_id", nullable = false)
    private Long deviceId;

    @Column(name = "activation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date activateDate;
}