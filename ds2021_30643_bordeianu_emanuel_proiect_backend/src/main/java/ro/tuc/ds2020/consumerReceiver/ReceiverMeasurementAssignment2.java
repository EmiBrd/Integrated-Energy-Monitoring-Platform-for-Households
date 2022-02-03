package ro.tuc.ds2020.consumerReceiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.configure.RabbitMQConfig;
import ro.tuc.ds2020.entities.Measurements;
import ro.tuc.ds2020.entities.Sensors;
import ro.tuc.ds2020.model.MeasurementAssignment2;
import ro.tuc.ds2020.repositories.MeasurementsRepository;
import ro.tuc.ds2020.repositories.SensorsRepository;
import java.util.Date;

@Component
public class ReceiverMeasurementAssignment2 {
    @Autowired
    private SimpMessagingTemplate template;

    private final MeasurementsRepository measurementsRepository;
    private final SensorsRepository sensorsRepository;

    public ReceiverMeasurementAssignment2(MeasurementsRepository measurementsRepository,
                                          SensorsRepository sensorsRepository)
    {
        this.measurementsRepository = measurementsRepository;
        this.sensorsRepository = sensorsRepository;
    }


    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receive(MeasurementAssignment2 measurementAssignment2)
    {
        Sensors sensor = sensorsRepository.findById(measurementAssignment2.getSensorId()).get();
        Measurements[] measurementsVect = measurementsRepository.findAll().toArray(new Measurements[0]);
        System.out.println("previous measurementAssign2 = " + measurementsVect[measurementsVect.length-1]);
        System.out.println("measurementAssign2 = " + measurementAssignment2);
        Measurements measurement = new Measurements(null,
                new Date(measurementAssignment2.getTimestamp().getYear()-1900,
                        measurementAssignment2.getTimestamp().getMonthValue()-1,
                        measurementAssignment2.getTimestamp().getDayOfMonth(), measurementAssignment2.getTimestamp().getHour(),
                        measurementAssignment2.getTimestamp().getMinute(), measurementAssignment2.getTimestamp().getSecond() ),
                measurementAssignment2.getMeasurementValue(), sensor);
        if((measurement.getEnergyConsumption() - measurementsVect[measurementsVect.length - 1].getEnergyConsumption() ) <
            sensor.getMaxValue() )
        {
            measurementsRepository.save(measurement);
            template.convertAndSend("/enablebroker/websocket/measurements/measurementt",
                    measurement );
        }
        else {
            template.convertAndSend("/enablebroker/websocket/measurements/msg",
//                    "Measurement with id: " + (measurementsVect[measurementsVect.length-1].getId()+1) +
//                            ", timestamp: " + measurement.getTimestamp() +
//                    ", energy consumption: " +measurement.getEnergyConsumption() + "\n - Measurement with id: " +
//                            measurementsVect[measurementsVect.length - 1].getId() + ", timestamp: "+
//                            measurementsVect[measurementsVect.length - 1].getTimestamp() + ", energy consumption: "+
//                            measurementsVect[measurementsVect.length - 1].getEnergyConsumption() +
//                            ", is equal or greater with/than " + sensor.getMaxValue()

                    "- Measurement with timestamp: " + (measurement.getTimestamp().getYear() + 1900) + "-" +
                            (measurement.getTimestamp().getMonth() + 1) + "-" + measurement.getTimestamp().getDate() +
                            " " + measurement.getTimestamp().getHours() + ":" + measurement.getTimestamp().getMinutes() +
                            ":" + measurement.getTimestamp().getSeconds() +
                            ", energy consumption: " +measurement.getEnergyConsumption() + "\n- Measurement with timestamp: "+
                            measurementsVect[measurementsVect.length - 1].getTimestamp() + ", energy consumption: "+
                            measurementsVect[measurementsVect.length - 1].getEnergyConsumption() +
                            ", is equal or greater with/than " + sensor.getMaxValue()
            );

            System.out.println("\nValoare maxima sensor = " + sensor.getMaxValue() +
                    ", diferenta = " + (measurement.getEnergyConsumption() -
                    measurementsVect[measurementsVect.length - 1].getEnergyConsumption() ) );
            //System.exit(0);
            return;
        }
    }

}
