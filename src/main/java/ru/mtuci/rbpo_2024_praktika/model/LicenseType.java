package ru.mtuci.rbpo_2024_praktika.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private Integer defaultDuration;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "licenseType", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("licenseType")
    private List<License> license;

}
