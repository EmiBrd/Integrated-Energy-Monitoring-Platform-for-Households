package ro.tuc.ds2020.dtos;

import ro.tuc.ds2020.entities.Devices;

public class DevicesBuilder {

    public static DevicesDTO toDeviceDTO(Devices device){
        return new DevicesDTO(device.getId(), device.getDescription(), device.getAddressLocation(),
                device.getMaxEnerCons(), device.getAvgBaselineEnerCons());
    }

    public static Devices toEntity(DevicesDTO devicesDTO){
        return new Devices(devicesDTO.getId(), devicesDTO.getDescription(), devicesDTO.getAddressLocation(),
                devicesDTO.getMaxEnerCons(), devicesDTO.getAvgBaselineEnerCons() );
    }

}
