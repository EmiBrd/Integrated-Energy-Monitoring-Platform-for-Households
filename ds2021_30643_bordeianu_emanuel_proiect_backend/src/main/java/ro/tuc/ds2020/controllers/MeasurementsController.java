package ro.tuc.ds2020.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ApiExceptionResponse;
import ro.tuc.ds2020.dtos.MeasurementsDTO;
import ro.tuc.ds2020.services.MeasurementsService;

@RestController
@RequestMapping("/measurements")
@CrossOrigin("*")
public class MeasurementsController {

    private final MeasurementsService measurementsService;

    public MeasurementsController(MeasurementsService measurementsService) {
        this.measurementsService = measurementsService;
    }

    @ApiOperation(value = "Returns a list of all measurements")
    @GetMapping("/findallmeasurements")
    public ResponseEntity findAllMeasurements(){
        System.out.println("\nfindAllMeasurements din MeasurementsController\n");
        //return ResponseEntity.status(HttpStatus.OK).body(measurementsService.findAll().toString() + "\n");
        return ResponseEntity.status(HttpStatus.OK).body(measurementsService.findAllDTO());
    }

    @ApiOperation(value = "Return a measurement")
    @GetMapping("/{id}")
    public ResponseEntity findMeasurementById(@ApiParam(value = "Requires an id for a measurement")
                                              @PathVariable Long id){
        //return ResponseEntity.status(HttpStatus.OK).body(measurementsService.findById(id));
        return ResponseEntity.status(HttpStatus.OK).body(measurementsService.findByIdDTO(id));
    }

    @ApiOperation(value = "It will add a measurement")
    @PostMapping("/insert")
    public ResponseEntity insertNewMeasurements(@ApiParam(value = "Requires a new measurement")
                                                @RequestBody MeasurementsDTO measurementsDTO){
        //return ResponseEntity.status(HttpStatus.OK).body(measurementsService.insertMeasurements(measurement));
        return ResponseEntity.status(HttpStatus.OK).body(measurementsService.insertMeasurementsDTO(measurementsDTO));
    }


    @ApiOperation(value = "It will update energy consumption of a measurement")
    @PutMapping("/update/{id}")
    public ResponseEntity updateMeasurement2(@ApiParam(value = "Requires id and new energ cons for a measurement")
                                             @PathVariable Long id, @RequestBody MeasurementsDTO measurementsDTO)
    {
        System.out.println(id);
        //System.out.println("energ consumption uptate = " + measurementsDTO.getEnergyConsumption());
        //return ResponseEntity.status(HttpStatus.OK).body(measurementsService.updateMeasurements2(id,dto));
        return ResponseEntity.status(HttpStatus.OK).body(measurementsService.updateMeasurements2DTO(id,measurementsDTO));
    }

    @ApiOperation(value = "It will delete a measurement from database")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMeasurementById(@ApiParam(value = "Requires an existant measurement")
                                                    @PathVariable Long id)
            throws ApiExceptionResponse
    {
        //return ResponseEntity.status(HttpStatus.OK).body(measurementsService.deleteMeasurements(id));
        return ResponseEntity.status(HttpStatus.OK).body(measurementsService.deleteMeasurementsDTO(id));
    }

    // aici pun fct pentru ziua curenta si fct pt celelalte zile
    //Double currentEnergyConsum(Long id)
    @ApiOperation(value = "Return a value = energy consumption in current day")
    @GetMapping("/clientconsumtoday/{id}")
    public ResponseEntity findClientActualConsumById(@ApiParam(value = "Requires an id for a client")
                                                     @PathVariable Long id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(measurementsService.currentEnergyConsumDTO(id) );
    }

    @ApiOperation(value = "Return a value = historical energy consumption")
    @GetMapping("/clientconsumhistorical/{id}")
    public ResponseEntity findClientHistoricalConsumById(@ApiParam(value = "Requires an id for a client")
                                                         @PathVariable Long id)
    {
        //return ResponseEntity.status(HttpStatus.OK).body(measurementsService.historicalEnergyConsum(id) );
        return ResponseEntity.status(HttpStatus.OK).body(measurementsService.historicalEnergyConsumDTO(id) );
    }

    @ApiOperation(value = "Return a value = energy consumption in that selected day")
    @GetMapping("/clientconsumbarchart/{id}/{ziua}")
    public ResponseEntity findClientConsumInSelectedDayBarChart(@ApiParam(value = "Requires an id for a client")
                                                                @PathVariable Long id,
                                                                @PathVariable String ziua)
    {
        return ResponseEntity.status(HttpStatus.OK).body(measurementsService.energieMasurataPeZiPentruBarChartVect(id, ziua) );
    }

    @ApiOperation(value = "Return a value = total energy consumption in that selected day")
    @GetMapping("/clientconsumtotalinaday/{id}/{ziua}")
    public ResponseEntity findClientConsumInSelectedDayTotal(@ApiParam(value = "Requires an id for a client")
                                                                @PathVariable Long id,
                                                                @PathVariable String ziua)
    {
        return ResponseEntity.status(HttpStatus.OK).body(measurementsService.energieMasurataPeZiPentruBarChart(id, ziua) );
    }


    @ApiOperation(value = "It will asociate a measurement to a sensor")
    @PutMapping("/asociaza/{idMeasurement}/{idSensor}")
    public ResponseEntity asociazaMeasurementUnuiSensor(@ApiParam(value = "Requires an existant measurement")
                                                       @PathVariable Long idMeasurement,
                                                   @PathVariable Long idSensor
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(measurementsService.asociazaSensor2DTO(idMeasurement, idSensor));
    }

    @ApiOperation(value = "It will delete asociation between a measurement and a sensor")
    @PutMapping("/stergeasocierea/{idMeasurement}/{idSensor}")
    public ResponseEntity stergeasociereaUnuiMeasurementSiAUnuiSensor(@ApiParam(value = "Requires an existant measurement")
                                                                          @PathVariable Long idMeasurement,
                                                                 @PathVariable Long idSensor
    ) {
        return ResponseEntity.status(HttpStatus.OK).
                body(measurementsService.stergeAsociereaMeasurementSensor2DTO(idMeasurement, idSensor));
    }

    @ApiOperation(value = "Returns a list of all measurements holded by a client")
    @GetMapping("/findallmeasurementsfromfile/{id}")
    public ResponseEntity findAllMeasurementsFromFile(
            @ApiParam(value = "Requires an existant client id")
            @PathVariable Long id)
    {
        System.out.println("\nfindAllMeasurementsFromFile din MeasurementsController\n");
        return ResponseEntity.status(HttpStatus.OK).body(measurementsService.listaMasuratoriAUnuiClientDTO(id));
    }

}

