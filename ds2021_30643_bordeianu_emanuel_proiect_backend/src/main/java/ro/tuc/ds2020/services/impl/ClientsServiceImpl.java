package ro.tuc.ds2020.services.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ro.tuc.ds2020.dtos.ClientsBuilder;
import ro.tuc.ds2020.dtos.ClientsDTO;
import ro.tuc.ds2020.entities.Clients;
import ro.tuc.ds2020.entities.Devices;
import ro.tuc.ds2020.repositories.ClientsRepository;
import ro.tuc.ds2020.repositories.DevicesRepository;
import ro.tuc.ds2020.repositories.MeasurementsRepository;
import ro.tuc.ds2020.repositories.SensorsRepository;
import ro.tuc.ds2020.services.ClientsService;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientsServiceImpl implements ClientsService {

    private final ClientsRepository clientsRepository;
    private final DevicesRepository devicesRepository;
    private final SensorsRepository sensorsRepository;
    private final MeasurementsRepository measurementsRepository;

    public ClientsServiceImpl(ClientsRepository clientsRepository, DevicesRepository devicesRepository,
                              SensorsRepository sensorsRepository, MeasurementsRepository measurementsRepository) {
        this.clientsRepository = clientsRepository;
        this.devicesRepository = devicesRepository;
        this.sensorsRepository = sensorsRepository;
        this.measurementsRepository = measurementsRepository;
    }

    @Override
    public ClientsDTO findByIdDTO(Long id) {
        return ClientsBuilder.toClientDTO(clientsRepository.findFirstById(id));
    }

    @Override
    public List<ClientsDTO> findAllDTO() {
        List<Clients> clientsList = clientsRepository.findAll();
        return clientsList.stream().map(ClientsBuilder::toClientDTO).
                collect(Collectors.toList());
    }

    @Override
    public ClientsDTO registerClientDTO(ClientsDTO clientsDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        clientsDTO.setActive(0);
        clientsDTO.setPassword(encoder.encode(clientsDTO.getPassword()));
        Clients client = ClientsBuilder.toEntity(clientsDTO);
        client = clientsRepository.save(client);
        return ClientsBuilder.toClientDTO(client);
    }

    @Override
    public ClientsDTO insertClientDTO(ClientsDTO clientsDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        clientsDTO.setPassword(encoder.encode("0000"));
        clientsDTO.setActive(0);
        Clients client = ClientsBuilder.toEntity(clientsDTO);
        client = clientsRepository.save(client);
        System.out.println("Parola este setata '0000' pentru clientul nou inserat\n");
        return ClientsBuilder.toClientDTO(client);
    }

    @Override
    @Transactional
    public ClientsDTO updateClientDTO(Long id, ClientsDTO clientsDTO) {
        Clients client = clientsRepository.findById(id).get();
        //Clients client = clientsRepository.findById(id).orElseThrow();
        if(clientsDTO.getName() == null){
            client.setName("_");
        }
        else{
            client.setName(clientsDTO.getName());
        }
        if(clientsDTO.getBirthDate() == null){
            client.setBirthDate(new Date(1950, 1, 1));
        }
        else{
            client.setBirthDate(clientsDTO.getBirthDate());
        }
        if(clientsDTO.getAddress() == null){
            client.setAddress("_");
        }
        else{
            client.setAddress(clientsDTO.getAddress());
        }
        if(clientsDTO.getEmail() == null){
            client.setEmail("nu_ai_pus_email@gmail.com");
        }
        else{
            client.setEmail(clientsDTO.getEmail());
        }
        if(clientsDTO.getUsername() == null){
            client.setUsername("_");
        }
        else{
            client.setUsername(clientsDTO.getUsername());
        }
        return ClientsBuilder.toClientDTO(client);
    }

    @Override
    @Transactional
    public ClientsDTO deleteClientByIdDTO(Long id) {
        Clients client = clientsRepository.findById(id).get();
        List<Devices> devicesList = devicesRepository.findAll();
        List<Devices> devicesListGood = new ArrayList<>();
        //System.out.println("\nLista de device-uri ale unui client\n");
        for (Devices deviceeee: devicesList) {
            if(deviceeee.getClient() != null){
                if(deviceeee.getClient().getId() == id){
                    devicesListGood.add(deviceeee);
                }
            }
        }
        Devices device0000;
        for (Devices deviceee: devicesListGood) {
            device0000 = devicesRepository.findFirstById(deviceee.getId());
            device0000.setClient(null);
            devicesRepository.save(device0000);
        }
        clientsRepository.delete(client);
        return ClientsBuilder.toClientDTO(client);
    }

    @Override
    public ClientsDTO findClientByUsernameDTO(String username){
        return ClientsBuilder.toClientDTO(clientsRepository.findFirstByUsername(username));
    }

}




