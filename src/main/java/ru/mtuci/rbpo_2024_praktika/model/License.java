package ru.mtuci.rbpo_2024_praktika.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "type_id", nullable = false)
    private Long typeId;

    @Column(name = "first_activation_date")
    @Temporal(TemporalType.DATE)
    private Date firstActivationDate;

    @Column(name = "ending_date")
    @Temporal(TemporalType.DATE)
    private Date endingDate;

    @Column(name = "blocked", nullable = false)
    private Boolean blocked;

    @Column(name = "device_count")
    private Integer deviceCount;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "description")
    private String description;
}
