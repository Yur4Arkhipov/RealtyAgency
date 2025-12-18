package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Agent;
import org.example.repository.AgentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AgentService {

    private final AgentRepository agentRepository;

    public List<Agent> findAll() {
        return agentRepository.findAll();
    }

    public Optional<Agent> findById(Long id) {
        return agentRepository.findById(id);
    }

    public Agent save(Agent agent) {
        return agentRepository.save(agent);
    }

    public void deleteById(Long id) {
        agentRepository.deleteById(id);
    }

    public List<Agent> findActiveAgents() {
        return agentRepository.findByActiveTrue();
    }

    public List<Agent> searchByName(String name) {
        return agentRepository.findByFullNameContainingIgnoreCase(name);
    }

    public long count() {
        return agentRepository.count();
    }

    public long countActive() {
        return agentRepository.findByActiveTrue().size();
    }
}

