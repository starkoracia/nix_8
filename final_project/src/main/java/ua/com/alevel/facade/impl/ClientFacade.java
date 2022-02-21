package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dto.PageDataRequest;
import ua.com.alevel.dto.PageDataResponse;
import ua.com.alevel.dto.entities.ClientDto;
import ua.com.alevel.entities.Client;
import ua.com.alevel.facade.FacadeClient;
import ua.com.alevel.services.impl.ClientService;

import java.util.List;
import java.util.Optional;

@Service
public class ClientFacade implements FacadeClient {

    ClientService clientService;

    public ClientFacade(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public Boolean create(ClientDto dto) {
        return clientService.create(dto.toClient());
    }

    @Override
    public void update(ClientDto dto) {
        clientService.update(dto.toClient());
    }

    @Override
    public void delete(ClientDto dto) {
        clientService.delete(dto.toClient());
    }

    @Override
    public boolean existById(Long id) {
        return clientService.existById(id);
    }

    @Override
    public Optional<ClientDto> findById(Long id) {
        Client client = clientService.findById(id).get();
        return Optional.ofNullable(ClientDto.toDto(client));
    }

    @Override
    public List<ClientDto> findAll() {
        return ClientDto.toDtoList(clientService.findAll());
    }

    @Override
    public Long count() {
        return clientService.count();
    }

    @Override
    public PageDataResponse<ClientDto> findAllFromRequest(PageDataRequest request) {
        return clientService.findAllFromRequest(request);
    }

    @Override
    public Long countNumberOfSearchMatches(PageDataRequest request) {
        return clientService.countNumberOfSearchMatches(request);
    }

    public ClientDto getLastCreatedClient() {
        return ClientDto.toDto(clientService.getLastCreatedClient());
    }

}
