package org.example.repository;

import org.example.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    List<Agent> findByActiveTrue();

    List<Agent> findByFullNameContainingIgnoreCase(String name);
}

