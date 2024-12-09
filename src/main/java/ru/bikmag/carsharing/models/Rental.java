package ru.bikmag.carsharing.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
@Data
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(nullable = false)
    private String username; // Кто арендует

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime; // Когда аренда завершена

    private double price;
}
