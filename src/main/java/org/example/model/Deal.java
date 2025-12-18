package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "deals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Объект недвижимости обязателен")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @NotNull(message = "Клиент обязателен")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @NotNull(message = "Агент обязателен")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @NotNull(message = "Дата сделки обязательна")
    @Column(nullable = false)
    private LocalDate dealDate;

    @NotNull(message = "Сумма сделки обязательна")
    @DecimalMin(value = "0.0", inclusive = false, message = "Сумма должна быть положительной")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(precision = 15, scale = 2)
    private BigDecimal commission;

    @NotNull(message = "Статус сделки обязателен")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DealStatus status = DealStatus.PENDING;

    @Column(length = 2000)
    private String notes;

    public enum DealStatus {
        PENDING("Ожидает"),
        IN_PROGRESS("В процессе"),
        COMPLETED("Завершена"),
        CANCELLED("Отменена");

        private final String displayName;

        DealStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}

