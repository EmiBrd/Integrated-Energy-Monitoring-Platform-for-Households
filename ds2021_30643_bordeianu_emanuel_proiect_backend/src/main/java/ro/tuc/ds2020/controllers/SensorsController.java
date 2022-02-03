package ro.tuc.ds2020.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.SensorsDTO;
import ro.tuc.ds2020.services.SensorsService;

@RestController
@RequestMapping("/sensors")
@CrossOrigin("*")
public class SensorsController {

    private final SensorsService sensorsService;

    public SensorsController(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @ApiOperation(value = "Returns a list of all sensors")
    @GetMapping("/findallsensors")
    public ResponseEntity findAllSensors(){
        return ResponseEntity.status(HttpStatus.OK).body(sensorsService.findAllDTO());
    }

    @ApiOperation(value = "Return a sensor")
    @GetMapping("/{id}")
    public ResponseEntity findSensorById(@ApiParam(value = "Requires an id for a sensor") @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(sensorsService.findByIdDTO(id));
    }

    @ApiOperation(value = "It will add a sensor")
    @PostMapping("/insert")
    public ResponseEntity insertNewSensors(@ApiParam(value = "Requires a new sensor") @RequestBody SensorsDTO sensorsDTO){
        return ResponseEntity.status(HttpStatus.OK).body(sensorsService.insertSensorsDTO(sensorsDTO));
    }

    @ApiOperation(value = "It will update a sensor")
    @PutMapping("/update/{id}")
    public ResponseEntity updateSensor(@ApiParam(value = "Requires id and new sensor")
                                       @PathVariable Long id, @RequestBody SensorsDTO sensorsDTO) {
        System.out.println(id);
        return ResponseEntity.status(HttpStatus.OK).body(sensorsService.updateSensorsDTO(id,sensorsDTO));
    }

    @ApiOperation(value = "It will delete a sensor from database")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteSensorById(@ApiParam(value = "Requires an existant sensor")@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(sensorsService.deleteSensorsDTO(id));
    }

    @ApiOperation(value = "It will asociate a sensor to a device")
    @PutMapping("/asociaza/{idSensor}/{idDevice}")
    public ResponseEntity asociazaSensorUnuiDevice(@ApiParam(value = "Requires an existant sensor")
                                                   @PathVariable Long idSensor, @PathVariable Long idDevice) {
        return ResponseEntity.status(HttpStatus.OK).body(sensorsService.asociazaDevice2DTO(idSensor, idDevice));
    }

    @ApiOperation(value = "It will delete asociation between a sensor and a device")
    @PutMapping("/stergeasocierea/{idSensor}/{idDevice}")
    public ResponseEntity stergeasociereaUnuiSensorSiAUnuiDevice(@ApiParam(value = "Requires an existant sensor")
                                                                 @PathVariable Long idSensor, @PathVariable Long idDevice) {
        return ResponseEntity.status(HttpStatus.OK).body(sensorsService.stergeAsociereaSensorDevice2DTO(idSensor, idDevice));
    }

    @ApiOperation(value = "Returns a list of all sensors holded by a client")
    @GetMapping("/clientssensors/{idClient}")
    public ResponseEntity findAllSensorsHoldedByAClient(
            @ApiParam(value = "Requires an existant client id") @PathVariable Long idClient){
        return ResponseEntity.status(HttpStatus.OK).body(sensorsService.listaSenzoriAUnuiClientDTO(idClient));
    }

}




