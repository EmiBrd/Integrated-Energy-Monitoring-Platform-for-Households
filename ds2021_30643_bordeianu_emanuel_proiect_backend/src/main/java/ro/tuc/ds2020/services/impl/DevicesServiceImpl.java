package ro.tuc.ds2020.services.impl;

import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.DevicesBuilder;
import ro.tuc.ds2020.dtos.DevicesDTO;
import ro.tuc.ds2020.entities.Clients;
import ro.tuc.ds2020.entities.Devices;
import ro.tuc.ds2020.entities.Measurements;
import ro.tuc.ds2020.entities.Sensors;
import ro.tuc.ds2020.repositories.ClientsRepository;
import ro.tuc.ds2020.repositories.DevicesRepository;
import ro.tuc.ds2020.repositories.MeasurementsRepository;
import ro.tuc.ds2020.repositories.SensorsRepository;
import ro.tuc.ds2020.services.DevicesService;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DevicesServiceImpl implements DevicesService {

    private final DevicesRepository devicesRepository;
    private final SensorsRepository sensorsRepository;
    private final ClientsRepository clientsRepository;
    private final MeasurementsRepository measurementsRepository;

    public DevicesServiceImpl(DevicesRepository devicesRepository, SensorsRepository sensorsRepository,
                              ClientsRepository clientsRepository, MeasurementsRepository measurementsRepository) {
        this.devicesRepository = devicesRepository;
        this.sensorsRepository = sensorsRepository;
        this.clientsRepository = clientsRepository;
        this.measurementsRepository = measurementsRepository;
    }

    @Override
    public DevicesDTO findByIdDTO(Long id) {
        return DevicesBuilder.toDeviceDTO(devicesRepository.findFirstById(id));
    }

    @Override
    public List<DevicesDTO> findAllDTO() {
        List<Devices> devicesList = devicesRepository.findAll();
        return devicesList.stream().map(DevicesBuilder::toDeviceDTO).
                collect(Collectors.toList());
    }

    @Override
    public List<DevicesDTO> findAllDevicesByClientIdDTO(Long idClient){
        List<Devices> devicesList = devicesRepository.findAllByClientId(3L);
        List<DevicesDTO> devicesDTOGood = new ArrayList<>();
        //System.out.println("\nAll devices by ID client");
        for(Devices d: devicesList){
            devicesDTOGood.add(DevicesBuilder.toDeviceDTO(d));
            //System.out.println("device = " + d.toString());
        }
        return devicesDTOGood;
    }

    @Override
    public DevicesDTO insertDevicesDTO(DevicesDTO devicesDTO) {
        Devices device = DevicesBuilder.toEntity(devicesDTO);
        device = devicesRepository.save(device);
        return DevicesBuilder.toDeviceDTO(device);
    }

    @Override
    @Transactional
    public DevicesDTO updateDevices2DTO(Long id, DevicesDTO devicesDTO) {
        Devices device = devicesRepository.findById(id).get();
        //Devices device = devicesRepository.findById(id).orElseThrow();
        if(devicesDTO.getDescription() == null){
            device.setDescription("_");
        }
        else{
            device.setDescription(devicesDTO.getDescription());
        }
        if(devicesDTO.getAddressLocation() == null){
            device.setAddressLocation("_");
        }
        else{
            device.setAddressLocation(devicesDTO.getAddressLocation());
        }
        if(devicesDTO.getMaxEnerCons() == null){
            device.setMaxEnerCons(0.0D);
        }
        else{
            device.setMaxEnerCons(devicesDTO.getMaxEnerCons());
        }
        if(devicesDTO.getAvgBaselineEnerCons() == null){
            device.setAvgBaselineEnerCons(0.0D);
        }
        else{
            device.setAvgBaselineEnerCons(devicesDTO.getAvgBaselineEnerCons());
        }
        return DevicesBuilder.toDeviceDTO(device);
    }

    @Override
    @Transactional
    public DevicesDTO deleteDevicesDTO(Long id) {
        Devices device = devicesRepository.findById(id).get();
        //Devices device = devicesRepository.findById(id).orElseThrow();
        Sensors sensor = sensorsRepository.findFirstByDevice(device);
        System.out.println("sensor = " + sensor + "\n");
        if(sensor == null){
            devicesRepository.delete(device);
            return DevicesBuilder.toDeviceDTO(device);
        }
        if( (sensor.getDevice().getId() > 0) ){
            sensor.setDevice(null);
            sensorsRepository.save(sensor);
        }
        devicesRepository.delete(device);
        return DevicesBuilder.toDeviceDTO(device);
    }

    @Override
    @Transactional
    public DevicesDTO asociazaClient2DTO(Long idDevice, Long idClient){
        Devices device = devicesRepository.findById(idDevice).get();
        Clients client = clientsRepository.findById(idClient).get();
        System.out.println("DevicesService: idClient = " + idClient);
        System.out.println("DevicesService: idDevice = " + idDevice);
        device.setClient(client);
        devicesRepository.save(device);
        return DevicesBuilder.toDeviceDTO(device);
    }

    @Override
    @Transactional
    public DevicesDTO stergeAsociereaDeviceClient2DTO(Long idDevice, Long idClient){
        Devices device = devicesRepository.findById(idDevice).get();
        Clients client = clientsRepository.findById(idClient).get();
        System.out.println("DevicesService stergeAsociereaDeviceClient: idClient = " + idClient);
        System.out.println("DevicesService stergeAsociereaDeviceClient: idDevice = " + idDevice);
        device.setClient(null);
        devicesRepository.save(device);
        return DevicesBuilder.toDeviceDTO(device);
    }

    @Override
    public List<DevicesDTO> listaDevicesAUnuiClientDTO(Long id){
        List<Devices> devicesList = devicesRepository.findAll();
        List<DevicesDTO> devicesListGood = new ArrayList<>();
        for (Devices devicee: devicesList) {
            if(devicee.getClient() != null){
                if(devicee.getClient().getId() == id){
                    devicesListGood.add(DevicesBuilder.toDeviceDTO(devicee));
                }
            }
        }
        System.out.println("Clientul cu id = " + id + " are urmatoare lista de device-uri");
        for (DevicesDTO devicee: devicesListGood) {
            System.out.println(devicee.getDescription() + "\n"+devicee.getAddressLocation() +
                    "\n"+devicee.getMaxEnerCons()+"\n"+devicee.getAvgBaselineEnerCons()+"\n");
        }
        return  devicesListGood;
    }

    @Override
    public List<Devices> findAllDevicesByClientId(Long idClient){
        List<Devices> devicesList = devicesRepository.findAllByClientId(idClient);
        return  devicesList;
    }

    @Override
    public Double[] valoriMasuratoriSubpunct2(Long idClient, Date dataAlegere, Integer cateDZileInUrma){
        //System.out.println("Devices service");
        //System.out.println("dateee year = " + dataAlegere.getYear());
        Integer sumaZiledataAlegere = ((dataAlegere.getYear() + 1900) * 365) + ((dataAlegere.getMonth() + 1) * 30) +
                dataAlegere.getDate();
        List<Measurements> measurementsList = measurementsRepository.findAll();
        List<Devices> devicesList = devicesRepository.findAllByClientId(idClient);
//            for(DevicesDTO d: devicesDTOList){
//                System.out.println("d = " + d.toString());
//            }
        Measurements[][] masuratoriDevicesRau = new Measurements[24][cateDZileInUrma]; //Measurements[24][devicesDTOList.size()];
        Double[][] valoriMasuratoriDevices = new Double[24][devicesList.size()];
        Integer[][] saStiuLaCatImpart = new Integer[24][devicesList.size()];
        Double[] valoriCumTrebuie = new Double[24];

        //System.out.println("\nInainte de alocari");
        for(int i =0; i<24; i++){
            valoriCumTrebuie[i] = 0.0D;
            for(int j =0; j<devicesList.size(); j++){
                saStiuLaCatImpart[i][j] = 0;
                valoriMasuratoriDevices[i][j] = 0.0D;
                masuratoriDevicesRau[i][j] = new Measurements(null, new Date(), 0.0D);
            }
        }

        int kappa = 0;
        for(Devices dDto: devicesList){
            for(Measurements m: measurementsList){
                Integer sumaOthers = ( (m.getTimestamp().getYear() + 1900) * 365) + ((m.getTimestamp().getMonth() + 1) * 30) +
                        m.getTimestamp().getDate();
                Integer diferentaZile = sumaZiledataAlegere - sumaOthers;
                if((diferentaZile <= cateDZileInUrma) && (diferentaZile >= 0)){
                    if(m.getSensor() != null){
                        if(m.getSensor().getDevice() != null){
                            if(m.getSensor().getDevice().getId() == dDto.getId()){
                                //System.out.println("m = " + m.toString());
                                masuratoriDevicesRau[m.getTimestamp().getHours()][kappa].setId(m.getId());
                                masuratoriDevicesRau[m.getTimestamp().getHours()][kappa].setSensor(m.getSensor());
                                masuratoriDevicesRau[m.getTimestamp().getHours()][kappa].setTimestamp(m.getTimestamp());
                                masuratoriDevicesRau[m.getTimestamp().getHours()][kappa]
                                        .setEnergyConsumption(
                                                masuratoriDevicesRau[m.getTimestamp().getHours()][kappa].getEnergyConsumption()+
                                                        m.getEnergyConsumption()
                                        );
                                //System.out.println("masuratoriDevicesRau = " +
                                //        masuratoriDevicesRau[m.getTimestamp().getHours()][kappa] + "\n");
                                saStiuLaCatImpart[m.getTimestamp().getHours()][kappa]++;
                            }
                        }
                    }
                }
            }
            kappa++;
        }

        for(int i = 0; i< devicesList.size(); i++){
            for(int j = 0; j<24; j++){
                if(saStiuLaCatImpart[j][i] > 0){
//                        System.out.println("masuratoriDevicesRau["+j+"]["+i+"].getEnergyConsumption() = " +
//                                masuratoriDevicesRau[j][i].getEnergyConsumption());
                    valoriMasuratoriDevices[j][i] += masuratoriDevicesRau[j][i].getEnergyConsumption() / saStiuLaCatImpart[j][i];
                }
                //System.out.println("valoriMasuratoriDevices["+j+"]["+i+"] = " + valoriMasuratoriDevices[j][i]);
            }
        }

        for(int i = 0; i<devicesList.size(); i++){
            for(int j =0; j<24; j++){
                valoriCumTrebuie[j] += valoriMasuratoriDevices[j][i];
            }
        }

        for(int j =0; j<24; j++){
            valoriCumTrebuie[j] /= 7;
        }

        return valoriCumTrebuie;
    }


}

