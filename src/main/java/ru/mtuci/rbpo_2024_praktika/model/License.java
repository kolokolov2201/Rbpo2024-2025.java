package ru.mtuci.rbpo_2024_praktika.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "license")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private ApplicationUser applicationUser;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private LicenseType licenseType;

    @Column(name = "first_activation_date")
    @Temporal(TemporalType.DATE)
    private Date firstActivationDate;

    @Column(name = "ending_date")
    @Temporal(TemporalType.DATE)
    private Date endingDate;

    @Column(name = "blocked")
    private Boolean blocked;

    @Column(name = "device_count")
    private Integer deviceCount;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private ApplicationUser owner;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "license", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("license")
    private List<LicenseHistory> licenseHistories;

    @OneToMany(mappedBy = "license", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("license")
    private List<DeviceLicense> deviceLicenses;

    public Boolean getBlocked() {
        return blocked != null ? blocked : false;
    }
}
