package ru.bikmag.carsharing.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cars")
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarStatus status = CarStatus.AVAILABLE; // Статус по умолчанию: доступен

    public enum CarStatus {
        AVAILABLE, RENTED, IN_REPAIR
    }

    @Column(nullable = false)
    private double pricePerMinute;
}
