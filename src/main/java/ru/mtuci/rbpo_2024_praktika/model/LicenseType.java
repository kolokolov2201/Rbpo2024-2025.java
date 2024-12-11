package ru.mtuci.rbpo_2024_praktika.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "licensetype")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LicenseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "default_duration", nullable = false)
    private Integer defaultDuration; // Длительность лицензии в днях или месяцах

    @Column(name = "description")
    private String description; // Описание лицензии
}
