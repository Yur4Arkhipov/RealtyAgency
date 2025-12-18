package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Client;
import org.example.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    public List<Client> findByType(Client.ClientType type) {
        return clientRepository.findByClientType(type);
    }

    public List<Client> searchByName(String name) {
        return clientRepository.findByFullNameContainingIgnoreCase(name);
    }

    public List<Client> searchByPhone(String phone) {
        return clientRepository.findByPhoneContaining(phone);
    }

    public long count() {
        return clientRepository.count();
    }
}

