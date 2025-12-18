package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Deal;
import org.example.model.Property;
import org.example.repository.DealRepository;
import org.example.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DealService {

    private final DealRepository dealRepository;
    private final PropertyRepository propertyRepository;

    public List<Deal> findAll() {
        return dealRepository.findAll();
    }

    public Optional<Deal> findById(Long id) {
        return dealRepository.findById(id);
    }

    public Deal save(Deal deal) {
        // Рассчитываем комиссию на основе ставки агента
        if (deal.getAgent() != null && deal.getAgent().getCommissionRate() != null && deal.getAmount() != null) {
            BigDecimal commission = deal.getAmount()
                    .multiply(BigDecimal.valueOf(deal.getAgent().getCommissionRate()))
                    .divide(BigDecimal.valueOf(100));
            deal.setCommission(commission);
        }

        Deal savedDeal = dealRepository.save(deal);

        // Обновляем статус объекта недвижимости при завершении сделки
        if (deal.getStatus() == Deal.DealStatus.COMPLETED && deal.getProperty() != null) {
            Property property = deal.getProperty();
            property.setStatus(Property.PropertyStatus.SOLD);
            propertyRepository.save(property);
        }

        return savedDeal;
    }

    public void deleteById(Long id) {
        dealRepository.deleteById(id);
    }

    public List<Deal> findByStatus(Deal.DealStatus status) {
        return dealRepository.findByStatus(status);
    }

    public List<Deal> findByAgent(Long agentId) {
        return dealRepository.findByAgentId(agentId);
    }

    public List<Deal> findByClient(Long clientId) {
        return dealRepository.findByClientId(clientId);
    }

    public List<Deal> findByProperty(Long propertyId) {
        return dealRepository.findByPropertyId(propertyId);
    }

    public List<Deal> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return dealRepository.findByDealDateBetween(startDate, endDate);
    }

    public long count() {
        return dealRepository.count();
    }

    public long countCompleted() {
        return dealRepository.findByStatus(Deal.DealStatus.COMPLETED).size();
    }

    public BigDecimal calculateTotalRevenue() {
        return dealRepository.findByStatus(Deal.DealStatus.COMPLETED).stream()
                .map(Deal::getCommission)
                .filter(c -> c != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

