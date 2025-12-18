package org.example.repository;

import org.example.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

    List<Deal> findByStatus(Deal.DealStatus status);

    List<Deal> findByAgentId(Long agentId);

    List<Deal> findByClientId(Long clientId);

    List<Deal> findByPropertyId(Long propertyId);

    List<Deal> findByDealDateBetween(LocalDate startDate, LocalDate endDate);

    List<Deal> findByAgentIdAndStatus(Long agentId, Deal.DealStatus status);
}

