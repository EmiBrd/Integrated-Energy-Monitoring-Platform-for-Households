package ro.tuc.ds2020.services;

import org.springframework.stereotype.Component;
import ro.tuc.ds2020.dtos.SensorsDTO;
import ro.tuc.ds2020.entities.Devices;
import ro.tuc.ds2020.entities.Sensors;
import java.util.List;

@Component
public interface SensorsService {
    SensorsDTO findByIdDTO(Long id);
    List<SensorsDTO> findAllDTO();
    SensorsDTO insertSensorsDTO(SensorsDTO sensorsDTO);
    SensorsDTO updateSensorsDTO(Long id, SensorsDTO sensorsDTO);
    SensorsDTO deleteSensorsDTO(Long id);
    SensorsDTO asociazaDevice2DTO(Long idSensor, Long idDevice);
    SensorsDTO stergeAsociereaSensorDevice2DTO(Long idSensor, Long idDevice);
    List<SensorsDTO> listaSenzoriAUnuiClientDTO(Long id);

}
