package ru.mtuci.rbpo_2024_praktika.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private ApplicationRole role;

    @OneToMany(mappedBy = "applicationUser", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("applicationUser")
    private List<Device> devices;

    @OneToMany(mappedBy = "applicationUser", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("applicationUser")
    private List<LicenseHistory> licenseHistories;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("owner")
    private List<License> ownedLicenses;

    @OneToMany(mappedBy = "applicationUser", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("applicationUser")
    private List<License> usedLicenses;
}
