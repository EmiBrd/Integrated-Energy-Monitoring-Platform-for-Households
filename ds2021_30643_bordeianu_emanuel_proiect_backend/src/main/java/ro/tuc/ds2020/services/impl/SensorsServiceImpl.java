package ro.tuc.ds2020.services.impl;

import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.*;
import ro.tuc.ds2020.entities.Devices;
import ro.tuc.ds2020.entities.Measurements;
import ro.tuc.ds2020.entities.Sensors;
import ro.tuc.ds2020.repositories.DevicesRepository;
import ro.tuc.ds2020.repositories.MeasurementsRepository;
import ro.tuc.ds2020.repositories.SensorsRepository;
import ro.tuc.ds2020.services.SensorsService;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorsServiceImpl implements SensorsService {

    private final SensorsRepository sensorsRepository;
    private final MeasurementsRepository measurementsRepository;
    private final DevicesRepository devicesRepository;

    public SensorsServiceImpl(SensorsRepository sensorsRepository, MeasurementsRepository measurementsRepository, DevicesRepository devicesRepository) {
        this.sensorsRepository = sensorsRepository;
        this.measurementsRepository = measurementsRepository;
        this.devicesRepository = devicesRepository;
    }

    @Override
    public SensorsDTO findByIdDTO(Long id) {
        return SensorsBuilder.toSensorsDTO(sensorsRepository.findFirstById(id));
    }

    @Override
    public List<SensorsDTO> findAllDTO() {
        List<Sensors> sensorsList = sensorsRepository.findAll();
        return sensorsList.stream().map(SensorsBuilder::toSensorsDTO).
                collect(Collectors.toList());
    }

    @Override
    public SensorsDTO insertSensorsDTO(SensorsDTO sensorsDTO) {
        Sensors sensor = SensorsBuilder.toEntity(sensorsDTO);
        sensor = sensorsRepository.save(sensor);
        return SensorsBuilder.toSensorsDTO(sensor);
    }

    @Override
    @Transactional
    public SensorsDTO updateSensorsDTO(Long id, SensorsDTO sensorsDTO) {
        Sensors sensor = sensorsRepository.findById(id).get();
        //Sensors sensor = sensorsRepository.findById(id).orElseThrow();
        if(sensorsDTO.getDescription() == null){
            sensor.setDescription("_");
        }
        else{
            sensor.setDescription(sensorsDTO.getDescription());
        }
        //sensor.setDescription(dto.getDescription());
        if(sensorsDTO.getMaxValue() == null){
            sensor.setMaxValue(0.0D);
        }
        else{
            sensor.setMaxValue(sensorsDTO.getMaxValue());
        }
        return SensorsBuilder.toSensorsDTO(sensor);
    }

    @Override
    @Transactional
    public SensorsDTO deleteSensorsDTO(Long id) {
        Sensors sensor = sensorsRepository.findById(id).get();
        //Sensors sensor = sensorsRepository.findById(id).orElseThrow();
        List<Measurements> measurementsList = measurementsRepository.findAll();
        List<Measurements> measurementsListGood = new ArrayList<>();

        for (Measurements measurementsss: measurementsList) {
            if(measurementsss.getSensor() != null){
                if(measurementsss.getSensor().getId() == id){
                    measurementsListGood.add(measurementsss);
                }
            }
        }

        Measurements measurement0000;
        for (Measurements measurementsss: measurementsListGood) {
            measurement0000 = measurementsRepository.findFirstById(measurementsss.getId());
            measurement0000.setSensor(null);
            measurementsRepository.save(measurement0000);
        }

        sensorsRepository.delete(sensor);
        return SensorsBuilder.toSensorsDTO(sensor);
    }

    @Override
    @Transactional
    public SensorsDTO asociazaDevice2DTO(Long idSensor, Long idDevice){
        Sensors sensor = sensorsRepository.findById(idSensor).get();
        Devices device = devicesRepository.findById(idDevice).get();
        System.out.println("SensorsService: idDevice = " + idDevice);
        System.out.println("SensorsService: idSensor = " + idSensor);
        sensor.setDevice(device);
        sensorsRepository.save(sensor);
        return SensorsBuilder.toSensorsDTO(sensor);
    }

    @Override
    @Transactional
    public SensorsDTO stergeAsociereaSensorDevice2DTO(Long idSensor, Long idDevice){
        Sensors sensor = sensorsRepository.findById(idSensor).get();
        Devices device = devicesRepository.findById(idDevice).get();
        System.out.println("SensorsService stergeAsociereaSensorDevice2: idSensor = " + idSensor);
        System.out.println("SensorsService stergeAsociereaSensorDevice2: idDevice = " + idDevice);
        sensor.setDevice(null);
        sensorsRepository.save(sensor);
        return SensorsBuilder.toSensorsDTO(sensor);
    }

    @Override
    public List<SensorsDTO> listaSenzoriAUnuiClientDTO(Long id){
        List<Sensors> sensorsList = sensorsRepository.findAll();
        List<SensorsDTO> sensorsListGood = new ArrayList<>();
        for (Sensors sensorsss: sensorsList) {
            if(sensorsss.getDevice() != null){
                if(sensorsss.getDevice().getClient() != null){
                    if(sensorsss.getDevice().getClient().getId() == id){
                        sensorsListGood.add(SensorsBuilder.toSensorsDTO(sensorsss));
                    }
                }
            }
        }
        System.out.println("Clientul cu id = " + id + " are urmatoare lista de senzori");
        for (SensorsDTO sensorsss: sensorsListGood) {
            System.out.println(sensorsss.getDescription() + "\n"+sensorsss.getMaxValue() + "\n");
        }

        return  sensorsListGood;
    }

}
