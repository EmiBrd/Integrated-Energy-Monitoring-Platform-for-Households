package ro.tuc.ds2020.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ApiExceptionResponse;
import ro.tuc.ds2020.dtos.DevicesDTO;
import ro.tuc.ds2020.services.DevicesService;

@RestController
@RequestMapping("/devices")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@CrossOrigin("*")
public class DevicesController {
    private final DevicesService devicesService;

    public DevicesController(DevicesService devicesService) {
        this.devicesService = devicesService;
    }

    @ApiOperation(value = "Returns a list of all devices")
    @GetMapping("/findalldevices")
    public ResponseEntity findAllDevices(){
        //return ResponseEntity.status(HttpStatus.OK).body(devicesService.findAll().toString() + "\n");
        return ResponseEntity.status(HttpStatus.OK).body(devicesService.findAllDTO());
    }

    @ApiOperation(value = "Return a device")
    @GetMapping("/{id}")
    public ResponseEntity findDeviceById(@ApiParam(value = "Requires an id of a device")
                                         @PathVariable Long id){
        //return ResponseEntity.status(HttpStatus.OK).body(devicesService.findById(id));
        return ResponseEntity.status(HttpStatus.OK).body(devicesService.findByIdDTO(id));
    }

    @ApiOperation(value = "It will add a device in database")
    @PostMapping("/insert")
    public ResponseEntity insertNewDevices(@ApiParam(value = "Requires a new device")
                                           @RequestBody DevicesDTO devicesDTO){
        //return ResponseEntity.status(HttpStatus.OK).body(devicesService.insertDevices(device));
        return ResponseEntity.status(HttpStatus.OK).body(devicesService.insertDevicesDTO(devicesDTO));
    }

    @ApiOperation(value = "It will update a device")
    @PutMapping("/update/{id}")
    public ResponseEntity updateNewDevice(@ApiParam(value = "Requires id and a device")
                                          @PathVariable Long id, @RequestBody DevicesDTO devicesDTO){
        System.out.println(id);
        //System.out.println("device description uptate = " + devicesDTO.getDescription());
        //return ResponseEntity.status(HttpStatus.OK).body(devicesService.updateDevices2(id,dto));
        return ResponseEntity.status(HttpStatus.OK).body(devicesService.updateDevices2DTO(id,devicesDTO));
    }

    @ApiOperation(value = "It will delete a device from database")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteDeviceById(@ApiParam(value = "Requires an existant device")@PathVariable Long id)
            throws ApiExceptionResponse {
        //return ResponseEntity.status(HttpStatus.OK).body(devicesService.deleteDevices(id));
        return ResponseEntity.status(HttpStatus.OK).body(devicesService.deleteDevicesDTO(id));
    }

    @ApiOperation(value = "It will asociate a device to a client")
    @PutMapping("/asociaza/{idDevice}/{idClient}")
    public ResponseEntity asociazaDeviceUnuiClient2(@ApiParam(value = "Requires an existant device")
                                                    @PathVariable Long idDevice,
                                                    @PathVariable Long idClient
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(devicesService.asociazaClient2DTO(idDevice, idClient));
    }

    @ApiOperation(value = "It will delete asociation between a device and a client")
    @PutMapping("/stergeasocierea/{idDevice}/{idClient}")
    public ResponseEntity stergeasociereaUnuiDeviceSiAUnuiClient(@ApiParam(value = "Requires an existant device")
                                                                 @PathVariable Long idDevice,
                                                                 @PathVariable Long idClient
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(devicesService.stergeAsociereaDeviceClient2DTO(idDevice,
                idClient));
    }

    @ApiOperation(value = "Returns a list of all devices holded by a client")
    @GetMapping("/clientsdevices/{idClient}")
    public ResponseEntity findAllDevicesHoldedByAClient(
            @ApiParam(value = "Requires an existant client id")
            @PathVariable Long idClient
    ){
        return ResponseEntity.status(HttpStatus.OK).body(devicesService.listaDevicesAUnuiClientDTO(idClient));
    }


}

