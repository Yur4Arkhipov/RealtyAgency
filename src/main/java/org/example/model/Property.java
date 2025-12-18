package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название обязательно")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Тип недвижимости обязателен")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyType propertyType;

    @NotBlank(message = "Адрес обязателен")
    @Column(nullable = false)
    private String address;

    @NotNull(message = "Цена обязательна")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена должна быть положительной")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Площадь обязательна")
    @DecimalMin(value = "0.0", inclusive = false, message = "Площадь должна быть положительной")
    @Column(nullable = false)
    private Double area;

    @Min(value = 1, message = "Количество комнат должно быть минимум 1")
    private Integer rooms;

    @Min(value = 1, message = "Этаж должен быть минимум 1")
    private Integer floor;

    @Min(value = 1, message = "Этажность дома должна быть минимум 1")
    private Integer totalFloors;

    @Column(length = 2000)
    private String description;

    @NotNull(message = "Статус обязателен")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyStatus status = PropertyStatus.AVAILABLE;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    public enum PropertyType {
        APARTMENT("Квартира"),
        HOUSE("Дом"),
        COMMERCIAL("Коммерческая недвижимость"),
        LAND("Земельный участок");

        private final String displayName;

        PropertyType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum PropertyStatus {
        AVAILABLE("Доступно"),
        RESERVED("Забронировано"),
        SOLD("Продано");

        private final String displayName;

        PropertyStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}

