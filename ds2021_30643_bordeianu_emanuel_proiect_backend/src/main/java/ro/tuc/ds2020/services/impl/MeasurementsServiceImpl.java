package ro.tuc.ds2020.services.impl;

import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.MeasurementsBuilder;
import ro.tuc.ds2020.dtos.MeasurementsDTO;
import ro.tuc.ds2020.entities.Measurements;
import ro.tuc.ds2020.entities.Sensors;
import ro.tuc.ds2020.repositories.MeasurementsRepository;
import ro.tuc.ds2020.repositories.SensorsRepository;
import ro.tuc.ds2020.services.MeasurementsService;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MeasurementsServiceImpl implements MeasurementsService {

    private final MeasurementsRepository measurementsRepository;
    private final SensorsRepository sensorsRepository;

    public MeasurementsServiceImpl(MeasurementsRepository measurementsRepository, SensorsRepository sensorsRepository) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsRepository = sensorsRepository;
    }

    @Override
    public MeasurementsDTO findByIdDTO(Long id) {
        return MeasurementsBuilder.toMeasurementsDTO(measurementsRepository.findFirstById(id));
    }

    @Override
    public List<Measurements> findAll() {
        return measurementsRepository.findAll();
    }

    @Override
    public List<MeasurementsDTO> findAllDTO() {
        List<Measurements> measurementsList = measurementsRepository.findAll();
        return measurementsList.stream().map(MeasurementsBuilder::toMeasurementsDTO).
                collect(Collectors.toList());
    }

    @Override
    public MeasurementsDTO insertMeasurementsDTO(MeasurementsDTO measurementsDTO) {
        Measurements measurement = MeasurementsBuilder.toEntity(measurementsDTO);
        measurement = measurementsRepository.save(measurement);
        return MeasurementsBuilder.toMeasurementsDTO(measurement);
    }

    @Override
    @Transactional
    public MeasurementsDTO updateMeasurements2DTO(Long id, MeasurementsDTO measurementsDTO) {
        Measurements measurement = measurementsRepository.findById(id).get();
        //Measurements measurement = measurementsRepository.findById(id).orElseThrow();
        measurement.setTimestamp(measurementsDTO.getTimestamp());
        measurement.setEnergyConsumption(measurementsDTO.getEnergyConsumption());
        return MeasurementsBuilder.toMeasurementsDTO(measurement);
    }

    @Override
    @Transactional
    public MeasurementsDTO deleteMeasurementsDTO(Long id) {
        Measurements measurement = measurementsRepository.findById(id).get();
        //Measurements measurement = measurementsRepository.findById(id).orElseThrow();
        measurement.setSensor(null);
        measurementsRepository.save(measurement);
        measurementsRepository.delete(measurement);
        return MeasurementsBuilder.toMeasurementsDTO(measurement);
    }

    @Override
    public List<MeasurementsDTO> currentEnergyConsumDTO(Long id){
        Double currentEnergy = 0.0D;
        Date data_de_azi = new Date();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String data_parametru1 = simpleDateFormat1.format(data_de_azi);
        System.out.println("\ndata_parametru1 din currentEnergyConsum = " + data_parametru1 + "\n");

        Date data_de_azi_good = new Date(1901-1900, 1-1, 1);
        data_de_azi_good.setYear(data_de_azi.getYear());
        data_de_azi_good.setMonth(data_de_azi.getMonth());
        data_de_azi_good.setDate(data_de_azi.getDate());

        List<Measurements> measurementsList = measurementsRepository.findAll();
        List<MeasurementsDTO> measurementsListToDay = new ArrayList<>();

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String data_parametru2 = simpleDateFormat2.format(data_de_azi_good);
        System.out.println("\ndata_parametru2 din currentEnergyConsum = " + data_parametru2 + "\n");

        for (Measurements measure: measurementsList) {
            if(measure.getSensor() != null){
                if(measure.getSensor().getDevice() != null){
                    if(measure.getSensor().getDevice().getClient() != null){
                        if(measure.getSensor().getDevice().getClient().getId() != null)
                        {
                            measure.getTimestamp().setHours(0);
                            measure.getTimestamp().setMinutes(0);
                            measure.getTimestamp().setSeconds(0);
                            //System.out.println("measure.getSensor().getDevice().getClient().getId() = " +
                            //        measure.getSensor().getDevice().getClient().getId() );
                            //System.out.println("measure.getTimestamp().toString() = " +
                            //        measure.getTimestamp().toString());
                            //System.out.println("data_parametru in Main = " + data_parametru);
                            if(measure.getTimestamp().toString().equals(data_parametru2) ){
                                if(measure.getSensor().getDevice().getClient().getId() == id)
                                {
                                    measurementsListToDay.add(MeasurementsBuilder.toMeasurementsDTO(measure));
                                    currentEnergy += measure.getEnergyConsumption();
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("currentEnergy din currentEnergyConsum = " + currentEnergy);
        return measurementsListToDay;
    }

    @Override
    public List<MeasurementsDTO> historicalEnergyConsumDTO(Long id){
        Double historicalEnergy = 0.0D;
        Date data_de_azi = new Date();
        Date data_de_azi_good = new Date(1901-1900, 1-1, 1);
        data_de_azi_good.setYear(data_de_azi.getYear());
        data_de_azi_good.setMonth(data_de_azi.getMonth());
        data_de_azi_good.setDate(data_de_azi.getDate());

        List<Measurements> measurementsList = measurementsRepository.findAll();
        List<MeasurementsDTO> measurementsListHistorical = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String data_parametru = simpleDateFormat.format(data_de_azi_good);
        System.out.println("\ndata_parametru din historicalEnergyConsum = " + data_parametru + "\n");

        for (Measurements measure: measurementsList) {
            if(measure.getTimestamp().getYear() < data_de_azi_good.getYear()){
                if(measure.getSensor() != null){
                    if(measure.getSensor().getDevice() != null){
                        if(measure.getSensor().getDevice().getClient() != null){
                            if(measure.getSensor().getDevice().getClient().getId() != null)
                            {
                                measure.getTimestamp().setHours(0);
                                measure.getTimestamp().setMinutes(0);
                                measure.getTimestamp().setSeconds(0);

                                if(!measure.getTimestamp().toString().equals(data_parametru) ){
                                    if(measure.getSensor().getDevice().getClient().getId() == id)
                                    {
                                        measurementsListHistorical.add(MeasurementsBuilder.toMeasurementsDTO(measure));
                                        historicalEnergy += measure.getEnergyConsumption();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(
                    (measure.getTimestamp().getYear() == data_de_azi_good.getYear() ) &&
                            (measure.getTimestamp().getMonth() < data_de_azi_good.getMonth() )
            ) {
                if(measure.getSensor() != null){
                    if(measure.getSensor().getDevice() != null){
                        if(measure.getSensor().getDevice().getClient() != null){
                            if(measure.getSensor().getDevice().getClient().getId() != null)
                            {
                                measure.getTimestamp().setHours(0);
                                measure.getTimestamp().setMinutes(0);
                                measure.getTimestamp().setSeconds(0);
                                if(!measure.getTimestamp().toString().equals(data_parametru) ){
                                    if(measure.getSensor().getDevice().getClient().getId() == id)
                                    {
                                        measurementsListHistorical.add(MeasurementsBuilder.toMeasurementsDTO(measure));
                                        historicalEnergy += measure.getEnergyConsumption();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(
                    (measure.getTimestamp().getYear() == data_de_azi_good.getYear() ) &&
                            (measure.getTimestamp().getMonth() == data_de_azi_good.getMonth() ) &&
                            (measure.getTimestamp().getDate() < data_de_azi_good.getDate() )
            ){
                if(measure.getSensor() != null){
                    if(measure.getSensor().getDevice() != null){
                        if(measure.getSensor().getDevice().getClient() != null){
                            if(measure.getSensor().getDevice().getClient().getId() != null)
                            {
                                measure.getTimestamp().setHours(0);
                                measure.getTimestamp().setMinutes(0);
                                measure.getTimestamp().setSeconds(0);
                                if(!measure.getTimestamp().toString().equals(data_parametru) ){
                                    if(measure.getSensor().getDevice().getClient().getId() == id)
                                    {
                                        measurementsListHistorical.add(MeasurementsBuilder.toMeasurementsDTO(measure));
                                        historicalEnergy += measure.getEnergyConsumption();
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        System.out.println("historicalEnergy din currentEnergyConsum = " + historicalEnergy);
        return measurementsListHistorical;
    }


    @Override
    public Double[] energieMasurataPeZiPentruBarChartVect(Long id, String ziua){
        String[] strings = ziua.split("-");
        Integer[] integers = new Integer[3];
        int i =0;
        for (String str: strings) {
            //System.out.println("str = " + str);
            integers[i] = Integer.parseInt(str);
            i++;
        }
        System.out.println();
        Double[] measurementsListGood = new Double[24];
        for(int j = 0; j<24; j++){
            measurementsListGood[j] = 0.0D;
        }
        Integer[] pentruCalculEnergieOra = new Integer[24];
        for(int j = 0; j<24; j++){
            pentruCalculEnergieOra[j] = 1;
        }

        List<Measurements> measurementsList = measurementsRepository.findAll();
        for(Measurements measure: measurementsList) {
            measure.getTimestamp().setSeconds(0);

            if( ( (measure.getTimestamp().getYear() + 1900 ) == integers[0] ) &&
                    ( (measure.getTimestamp().getMonth() + 1) == integers[1] ) &&
                    (measure.getTimestamp().getDate() == integers[2] )
            ) {
                if( (measure.getTimestamp().getHours() == 0) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        /*
                                        System.out.println("barChart measure.getTimestamp().getMonth() = " +
                                                measure.getTimestamp().getMonth()
                                                + "\nmeasure.getTimestamp().getDate() = " +
                                                measure.getTimestamp().getDate() +
                                                "\nmeasure.getTimestamp().getHours() = " +
                                                measure.getTimestamp().getHours() +
                                                "\nidClient = " + id + "\n");
                                        */
                                        measurementsListGood[0] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[0]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 1) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[1] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[1]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 2) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[2] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[2]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 3) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[3] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[3]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 4) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[4] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[4]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 5) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[5] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[5]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 6) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[6] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[6]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 7) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[7] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[7]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 8) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[8] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[8]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 9) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[9] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[9]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 10) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[10] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[10]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 11) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[11] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[11]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 12) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[12] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[13]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 13) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[13] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[13]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 14) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[14] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[14]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 15) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[15] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[15]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 16) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[16] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[16]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 17) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[17] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[17]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 18) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[18] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[18]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 19) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[19] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[19]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 20) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[20] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[20]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 21) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[21] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[21]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 22) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[22] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[22]++;
                                    }
                                }
                            }
                        }
                    }
                }
                if( (measure.getTimestamp().getHours() == 23) ){
                    if (measure.getSensor() != null) {
                        if (measure.getSensor().getDevice() != null) {
                            if (measure.getSensor().getDevice().getClient() != null) {
                                if (measure.getSensor().getDevice().getClient().getId() != null) {
                                    if(measure.getSensor().getDevice().getClient().getId() == id) {
                                        measurementsListGood[23] += measure.getEnergyConsumption();
                                        pentruCalculEnergieOra[23]++;
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        for(int j = 0; j<24; j++){
            measurementsListGood[j] /= pentruCalculEnergieOra[j];
        }

        return measurementsListGood;
    }

    @Override
    public Double energieMasurataPeZiPentruBarChart(Long id, String ziua){
        //String dataa = "2021-10-30";
        String[] strings = ziua.split("-");
        Integer[] integers = new Integer[3];
        int i =0;
        for (String str: strings) {
            integers[i] = Integer.parseInt(str);
            i++;
        }
        /*
        for (Integer integer: integers) {
            System.out.println("integer = " + integer);
        }
        System.out.println("\n\n");*/

        Double energieConsumata = 0.0D;
        List<Measurements> measurementsList = measurementsRepository.findAll();
        for(Measurements measure: measurementsList) {
            measure.getTimestamp().setHours(0);
            measure.getTimestamp().setMinutes(0);
            measure.getTimestamp().setSeconds(0);
            //System.out.println("measure.getTimestamp().getYear() = " + measure.getTimestamp().getYear());
            //System.out.println("ziua.getYear() = " + integers[0] );
            if( ( (measure.getTimestamp().getYear() + 1900 ) == integers[0] ) &&
                    ( (measure.getTimestamp().getMonth() + 1) == integers[1] ) &&
                    (measure.getTimestamp().getDate() == integers[2] )
            ) {
                if (measure.getSensor() != null) {
                    if (measure.getSensor().getDevice() != null) {
                        if (measure.getSensor().getDevice().getClient() != null) {
                            if (measure.getSensor().getDevice().getClient().getId() != null) {
                                energieConsumata += measure.getEnergyConsumption();
                            }
                        }
                    }
                }
            }
        }
        System.out.println("energieConsumata MeasurementsService= " + energieConsumata);
        return energieConsumata;
    }

    @Override
    @Transactional
    public MeasurementsDTO asociazaSensor2DTO(Long idMeasurement, Long idSensor){
        Measurements measurement = measurementsRepository.findById(idMeasurement).get();
        Sensors sensor = sensorsRepository.findById(idSensor).get();

        System.out.println("MeasurementsService: idSensor = " + idSensor);
        System.out.println("MeasurementsService: idMeasurement = " + idMeasurement);

        measurement.setSensor(sensor);
        measurementsRepository.save(measurement);
        return MeasurementsBuilder.toMeasurementsDTO(measurement);
    }

    @Override
    @Transactional
    public MeasurementsDTO stergeAsociereaMeasurementSensor2DTO(Long idMeasurement, Long idSensor){
        Measurements measurement = measurementsRepository.findById(idMeasurement).get();
        Sensors sensor = sensorsRepository.findById(idSensor).get();
        System.out.println("MeasurementsService stergeAsociereaMeasurementSensor2DTO: idMeasurement = " + idMeasurement);
        System.out.println("MeasurementsService stergeAsociereaMeasurementSensor2DTO: idSensor = " + idSensor);

        measurement.setSensor(null);
        measurementsRepository.save(measurement);
        return MeasurementsBuilder.toMeasurementsDTO(measurement);
    }

    @Override
    public List<MeasurementsDTO> listaMasuratoriAUnuiClientDTO(Long id){
        List<Measurements> measurementsList = measurementsRepository.findAll();
        List<MeasurementsDTO> measurementsDTOListGood = new ArrayList<>();

        for (Measurements mm: measurementsList) {
            if(mm.getSensor() != null){
                if(mm.getSensor().getDevice() != null){
                    if(mm.getSensor().getDevice().getClient() != null){
                        if(mm.getSensor().getDevice().getClient().getId() == id){
                            measurementsDTOListGood.add(MeasurementsBuilder.toMeasurementsDTO(mm));
                        }
                    }
                }
            }
        }

        System.out.println("Clientul cu id = " + id + " are urmatoarea lista de masuratori");
        for (MeasurementsDTO mmdto: measurementsDTOListGood) {
            System.out.println("ID = " + mmdto.getId() + ", date = "+ mmdto.getTimestamp() +
                    ",  valoare masurata " + mmdto.getEnergyConsumption());
        }

        return  measurementsDTOListGood;
    }

    @Override
    public List<Measurements> findAllMeasurementsByClientId(Long idClient){
        List<Measurements> measurementsList = measurementsRepository.findAll();
        List<Measurements> measurementsListGood = new ArrayList<>();
        for(Measurements m: measurementsList){
            if(m.getSensor() != null){
                if(m.getSensor().getDevice() != null){
                    if(m.getSensor().getDevice().getClient() != null){
                        if(m.getSensor().getDevice().getClient().getId() == idClient){
                            measurementsListGood.add(m);
                        }
                    }
                }
            }
        }
        return  measurementsListGood;
    }

    @Override
    public List<MeasurementsDTO> findAllMeasurementsDTOByClientId(Long idClient){
        List<Measurements> measurementsList = measurementsRepository.findAll();
        List<MeasurementsDTO> measurementsDTOListGood = new ArrayList<>();
        for(Measurements m: measurementsList){
            if(m.getSensor() != null){
                if(m.getSensor().getDevice() != null){
                    if(m.getSensor().getDevice().getClient() != null){
                        if(m.getSensor().getDevice().getClient().getId() == idClient){
                            measurementsDTOListGood.add(MeasurementsBuilder.toMeasurementsDTO(m));
                        }
                    }
                }
            }
        }

        return  measurementsDTOListGood;
    }


}
