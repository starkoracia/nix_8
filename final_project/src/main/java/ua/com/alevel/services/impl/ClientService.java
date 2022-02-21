package ua.com.alevel.services.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.impl.ClientDao;
import ua.com.alevel.dto.PageDataRequest;
import ua.com.alevel.dto.PageDataResponse;
import ua.com.alevel.dto.entities.ClientDto;
import ua.com.alevel.entities.Client;
import ua.com.alevel.services.ServiceClient;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements ServiceClient {

    ClientDao clientDao;

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public Boolean create(Client client) {
        return clientDao.create(client);
    }

    @Override
    public void update(Client client) {
        clientDao.update(client);
    }

    @Override
    public void delete(Client client) {
        clientDao.delete(client);
    }

    @Override
    public boolean existById(Long id) {
        return clientDao.existById(id);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientDao.findById(id);
    }

    @Override
    public List<Client> findAll() {
        return clientDao.findAll();
    }

    @Override
    public Long count() {
        return clientDao.count();
    }

    public PageDataResponse<ClientDto> findAllFromRequest(PageDataRequest request) {
        List<Client> clients = clientDao.findAllFromRequest(request);
        PageDataResponse<ClientDto> dataResponse = new PageDataResponse<>();
        dataResponse.setDtoEntities(ClientDto.toDtoList(clients));
        if(request.getSearchString().equals("")) {
            dataResponse.setAmountOfElements(count().intValue());
        } else {
            dataResponse.setAmountOfElements(clients.size());
        }
        return dataResponse;
    }

    public Long countNumberOfSearchMatches(PageDataRequest request) {
        return clientDao.countNumberOfSearchMatches(request);
    }

    public Client getLastCreatedClient() {
        return clientDao.getLastCreatedClient();
    }

}
