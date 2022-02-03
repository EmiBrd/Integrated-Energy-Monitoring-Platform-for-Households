package ro.tuc.ds2020.services;

import org.springframework.stereotype.Component;
import ro.tuc.ds2020.dtos.ClientsDTO;
import java.util.List;

@Component
public interface ClientsService {
    ClientsDTO findByIdDTO(Long id);
    //ClientsDTO findByNameDTO(String name);
    List<ClientsDTO> findAllDTO();
    ClientsDTO registerClientDTO(ClientsDTO clientsDTO);
    ClientsDTO insertClientDTO(ClientsDTO clientsDTO);
    ClientsDTO updateClientDTO(Long id, ClientsDTO clientsDTO);
    ClientsDTO deleteClientByIdDTO(Long id);

    ClientsDTO findClientByUsernameDTO(String username);
}

