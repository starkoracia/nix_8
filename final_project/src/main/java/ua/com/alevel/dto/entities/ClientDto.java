package ua.com.alevel.dto.entities;

import lombok.Data;
import ua.com.alevel.entities.Client;

import java.util.List;

@Data
public class ClientDto extends BaseDto<Client> {
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private Boolean isSupplier;
    private String recommendation;
    private String annotation;

    public Client toClient() {
        Client client = new Client();
        client.setId(this.id);
        client.setIsSupplier(this.isSupplier);
        client.setMobile(this.mobile);
        client.setEmail(this.email);
        client.setName(this.name);
        client.setAnnotation(this.annotation);
        client.setRecommendation(this.recommendation);
        return client;
    }

    public static ClientDto toDto(Client client) {
        if(client == null) {
            return null;
        }
        ClientDto dto = new ClientDto();
            dto.setId(client.getId());
            dto.setIsSupplier(client.getIsSupplier());
            dto.setMobile(client.getMobile());
            dto.setEmail(client.getEmail());
            dto.setName(client.getName());
            dto.setAnnotation(client.getAnnotation());
            dto.setRecommendation(client.getRecommendation());
        return dto;
    }

    public static List<ClientDto> toDtoList(List<Client> clients) {
        return clients.stream().map(ClientDto::toDto).toList();
    }

}
