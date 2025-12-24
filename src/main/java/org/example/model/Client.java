package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

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

    @Enumerated(EnumType.STRING)
    private ClientType clientType = ClientType.BUYER;

    @Column(length = 1000)
    private String notes;

    public enum ClientType {
        BUYER("Покупатель"),
        SELLER("Продавец"),
        BOTH("Покупатель/Продавец");

        private final String displayName;

        ClientType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}

