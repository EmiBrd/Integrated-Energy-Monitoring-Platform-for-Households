package ro.tuc.ds2020.services;

import org.springframework.stereotype.Component;
import ro.tuc.ds2020.dtos.MeasurementsDTO;
import ro.tuc.ds2020.entities.Measurements;
import java.util.List;

@Component
public interface MeasurementsService {
    List<Measurements> findAll();
    Double[] energieMasurataPeZiPentruBarChartVect(Long id, String ziua);
    Double energieMasurataPeZiPentruBarChart(Long id, String ziua);

    MeasurementsDTO findByIdDTO(Long id);
    List<MeasurementsDTO> findAllDTO();
    MeasurementsDTO insertMeasurementsDTO(MeasurementsDTO measurementsDTO);
    MeasurementsDTO updateMeasurements2DTO(Long id, MeasurementsDTO measurementsDTO);
    MeasurementsDTO deleteMeasurementsDTO(Long id);

    List<MeasurementsDTO> currentEnergyConsumDTO(Long id);
    List<MeasurementsDTO> historicalEnergyConsumDTO(Long id);

    MeasurementsDTO asociazaSensor2DTO(Long idMeasurement, Long idSensor);
    MeasurementsDTO stergeAsociereaMeasurementSensor2DTO(Long idMeasurement, Long idSensor);

    List<MeasurementsDTO> listaMasuratoriAUnuiClientDTO(Long id);

    List<Measurements> findAllMeasurementsByClientId(Long idClient);
    List<MeasurementsDTO> findAllMeasurementsDTOByClientId(Long idClient);

}
