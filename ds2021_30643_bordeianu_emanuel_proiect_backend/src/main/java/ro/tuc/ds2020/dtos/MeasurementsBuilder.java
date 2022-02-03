package ro.tuc.ds2020.dtos;

import ro.tuc.ds2020.entities.Measurements;

public class MeasurementsBuilder {

    public static MeasurementsDTO toMeasurementsDTO(Measurements measurement){
        return new MeasurementsDTO(measurement.getId(), measurement.getTimestamp(),
                measurement.getEnergyConsumption() );
    }

    public static Measurements toEntity(MeasurementsDTO measurementsDTO){
        return new Measurements(measurementsDTO.getId(), measurementsDTO.getTimestamp(),
                measurementsDTO.getEnergyConsumption() );
    }

}
