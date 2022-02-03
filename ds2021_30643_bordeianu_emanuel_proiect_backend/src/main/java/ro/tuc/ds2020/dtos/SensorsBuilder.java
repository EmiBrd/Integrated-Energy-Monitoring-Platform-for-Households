package ro.tuc.ds2020.dtos;

import ro.tuc.ds2020.entities.Sensors;

public class SensorsBuilder {

    public static SensorsDTO toSensorsDTO(Sensors sensor){
        return new SensorsDTO(sensor.getId(), sensor.getDescription(), sensor.getMaxValue());
    }

    public static Sensors toEntity(SensorsDTO sensorsDTO){
        return new Sensors(sensorsDTO.getId(), sensorsDTO.getDescription(), sensorsDTO.getMaxValue() );
    }

}
