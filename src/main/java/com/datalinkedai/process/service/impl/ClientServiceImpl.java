package com.datalinkedai.process.service.impl;

import com.datalinkedai.process.domain.Client;
import com.datalinkedai.process.repository.ClientRepository;
import com.datalinkedai.process.service.ClientService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client) {
        log.debug("Request to save Client : {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        log.debug("Request to save Client : {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> partialUpdate(Client client) {
        log.debug("Request to partially update Client : {}", client);

        return clientRepository
            .findById(client.getId())
            .map(existingClient -> {
                if (client.getName() != null) {
                    existingClient.setName(client.getName());
                }
                if (client.getAddress() != null) {
                    existingClient.setAddress(client.getAddress());
                }
                if (client.getDescription() != null) {
                    existingClient.setDescription(client.getDescription());
                }
                if (client.getBusinessStartDate() != null) {
                    existingClient.setBusinessStartDate(client.getBusinessStartDate());
                }
                if (client.getBusinessEndDate() != null) {
                    existingClient.setBusinessEndDate(client.getBusinessEndDate());
                }
                if (client.getPayout() != null) {
                    existingClient.setPayout(client.getPayout());
                }
                if (client.getStatus() != null) {
                    existingClient.setStatus(client.getStatus());
                }
                if (client.getRemarks() != null) {
                    existingClient.setRemarks(client.getRemarks());
                }
                if (client.getTaskName() != null) {
                    existingClient.setTaskName(client.getTaskName());
                }
                if (client.getTaskType() != null) {
                    existingClient.setTaskType(client.getTaskType());
                }

                return existingClient;
            })
            .map(clientRepository::save);
    }

    @Override
    public List<Client> findAll() {
        log.debug("Request to get all Clients");
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findOne(String id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}
