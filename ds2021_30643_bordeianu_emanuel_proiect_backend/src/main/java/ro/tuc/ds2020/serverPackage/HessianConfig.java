package ro.tuc.ds2020.serverPackage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;
import ro.tuc.ds2020.services.ClientsService;
import ro.tuc.ds2020.services.DevicesService;
import ro.tuc.ds2020.services.MeasurementsService;

@Configuration
public class HessianConfig {

    private final MeasurementsService measurementsService;
    private final DevicesService devicesService;
    private final ClientsService clientsService;

    public HessianConfig(MeasurementsService measurementsService, DevicesService devicesService, ClientsService clientsService) {
        this.measurementsService = measurementsService;
        this.devicesService = devicesService;
        this.clientsService = clientsService;
    }

//    @Bean(name = "/getTheClientHourlyHistoricalEnergy")
//    RemoteExporter getTheClientHourlyHistoricalEnergyConsumptionOverDDaysInThePast() {
//        HessianServiceExporter exporter1 = new HessianServiceExporter();
//        exporter1.setService(new MeasureValueImpl(measurementsService, devicesService, clientsService));
//        exporter1.setServiceInterface(MeasureValue.class);
//        return exporter1;
//    }
//
//    @Bean(name = "/getTheAveragedEnergyConsumptionForTheClientOverThePastWeek")
//    RemoteExporter getTheAveragedEnergyConsumptionForTheClientOverThePastWeek() {
//        HessianServiceExporter exporter2 = new HessianServiceExporter();
//        exporter2.setService(new MeasureValueImpl(measurementsService, devicesService, clientsService));
//        exporter2.setServiceInterface(MeasureValue.class);
//        return exporter2;
//    }
//
//    @Bean(name = "/getBestTimeToStartConsideringBaselineAndProgramDuration")
//    RemoteExporter getBestTimeToStartConsideringBaselineAndProgramDuration() {
//        HessianServiceExporter exporter3 = new HessianServiceExporter();
//        exporter3.setService(new MeasureValueImpl(measurementsService, devicesService, clientsService));
//        exporter3.setServiceInterface(MeasureValue.class);
//        return exporter3;
//    }

    @Bean(name = "/loginHessian")
    RemoteExporter loginClientCredentials() {
        HessianServiceExporter exporter4 = new HessianServiceExporter();
        exporter4.setService(new MeasureValueImpl(measurementsService, devicesService, clientsService));
        exporter4.setServiceInterface(MeasureValue.class);
        return exporter4;
    }

}
