package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "agents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ФИО обязательно")
    @Column(nullable = false)
    private String fullName;

    @NotBlank(message = "Телефон обязателен")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Некорректный формат телефона")
    @Column(nullable = false)
    private String phone;

    @Email(message = "Некорректный email")
    private String email;

    @DecimalMin(value = "0.0", message = "Комиссия не может быть отрицательной")
    @DecimalMax(value = "100.0", message = "Комиссия не может превышать 100%")
    private Double commissionRate;

    @Column(length = 1000)
    private String bio;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String photoUrl;

    private Boolean active = true;
}

