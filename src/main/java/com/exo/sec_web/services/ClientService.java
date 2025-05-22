package com.exo.sec_web.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exo.sec_web.models.Client;
import com.exo.sec_web.repositories.ClientRepository;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> rechercherClients() {
        return this.clientRepository.findAll();
    }

}
