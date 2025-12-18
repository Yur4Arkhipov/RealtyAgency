package org.example.repository;

import org.example.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByClientType(Client.ClientType clientType);

    List<Client> findByFullNameContainingIgnoreCase(String name);

    List<Client> findByPhoneContaining(String phone);
}

