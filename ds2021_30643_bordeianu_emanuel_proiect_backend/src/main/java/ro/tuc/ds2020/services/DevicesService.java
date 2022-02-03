package ro.tuc.ds2020.services;

import org.springframework.stereotype.Component;
import ro.tuc.ds2020.dtos.DevicesDTO;
import ro.tuc.ds2020.entities.Devices;
import java.util.Date;
import java.util.List;

@Component
public interface DevicesService {
    DevicesDTO findByIdDTO(Long id);
    List<DevicesDTO> findAllDTO();
    DevicesDTO insertDevicesDTO(DevicesDTO devicesDTO);
    DevicesDTO updateDevices2DTO(Long id, DevicesDTO devicesDTO);
    DevicesDTO deleteDevicesDTO(Long id);
    DevicesDTO asociazaClient2DTO(Long idDevice, Long idClient);
    DevicesDTO stergeAsociereaDeviceClient2DTO(Long idDevice, Long idClient);
    List<DevicesDTO> listaDevicesAUnuiClientDTO(Long id);

    List<DevicesDTO> findAllDevicesByClientIdDTO(Long idClient);

    List<Devices> findAllDevicesByClientId(Long idClient);
    Double[] valoriMasuratoriSubpunct2(Long idClient, Date dataAlegere, Integer cateDZileInUrma);

}
