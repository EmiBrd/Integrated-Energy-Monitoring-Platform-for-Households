package ro.tuc.ds2020.dtos;

import ro.tuc.ds2020.entities.Clients;

public class ClientsBuilder {

    public static ClientsDTO toClientDTO(Clients client){
        return new ClientsDTO(client.getId(), client.getName(), client.getBirthDate(),
                client.getAddress(), client.getEmail(), client.getUsername(),
                client.getPassword(), client.getActive() );
    }

    public static Clients toEntity(ClientsDTO clientsDTO){
        return new Clients(clientsDTO.getId(), clientsDTO.getName(), clientsDTO.getBirthDate(),
                clientsDTO.getAddress(), clientsDTO.getEmail(), clientsDTO.getUsername(),
                clientsDTO.getPassword(), clientsDTO.getActive());
    }

}
