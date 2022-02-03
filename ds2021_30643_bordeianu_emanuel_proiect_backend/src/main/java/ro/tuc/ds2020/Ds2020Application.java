package ro.tuc.ds2020;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import ro.tuc.ds2020.entities.*;
import ro.tuc.ds2020.repositories.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootApplication
@Validated
public class Ds2020Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Ds2020Application.class);
    }

    public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime todayDateTime = LocalDateTime.now();
        ZonedDateTime currentDateTime = todayDateTime.atZone(ZoneId.of("Europe/London") );
        System.out.println("Time in London::");
        System.out.println(dateTimeFormatter.format(currentDateTime));

        SpringApplication.run(Ds2020Application.class, args);
    }

    @Bean
    CommandLineRunner init(
                            AdminsRepository adminsRepository,
                            ClientsRepository clientsRepository, SensorsRepository sensorsRepository,
                            DevicesRepository devicesRepository,
                            MeasurementsRepository measurementsRepository
                            //, DevicesService devicesService
                            //, MeasurementsService measurementsService
    ){
        return args->{

            System.out.println();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            Admins admin0 = new Admins(null, "admin0@gmail.com", "admin0",
                    "0000", "0700000000", 0);
            String encodedPassword_u0 = encoder.encode(admin0.getPassword());
            admin0.setPassword(encodedPassword_u0);
            adminsRepository.save(admin0);
            System.out.println("admin0: id = " + admin0.getId() + ", email = " + admin0.getEmail()+", username = "+admin0.getUsername()+
                    ", password = " + admin0.getPassword());


            // La Date ne adauga 1900 de ani. Luna ianuarie e luna 0. Ziua, orele, minutele incep normal

            //Clients client0 = new Clients(null, "client_0", "01.01.1999",
            //        "Trandafirilor", "client0@yahoo.com", "client0",
            //        "0000", false);
            Clients client_agent0 = new Clients(null, "client_agent0",
                    new Date(1970-1900, 1-1, 1),
                    "Crailor", "client_agent0@yahoo.com", "client_agent0",
                    "aaaa", 0);
            encodedPassword_u0 = encoder.encode(client_agent0.getPassword());
            client_agent0.setPassword(encodedPassword_u0);
            clientsRepository.save(client_agent0);
            System.out.println("client: client_id = " + client_agent0.getId() + ", email = " + client_agent0.getEmail()+
                    ", username = " + client_agent0.getUsername() + ", password = " + client_agent0.getPassword() +
                    ", name = " + client_agent0.getName());


            Devices device_agent0 = new Devices(null, "Samsung J5 2017", "Bulevardul Muncii",
                    40.0D, 20.0D, null);
            device_agent0.setClient(client_agent0);
            devicesRepository.save(device_agent0);

//            Devices device_agent1 = new Devices(null, "Samsung J7 2019", "Bulevardul Petalelor",
//                    45.0D, 25.0D, null);
//            device_agent1.setClient(client_agent0);
//            devicesRepository.save(device_agent1);


            Sensors sensor_agent0 = new Sensors(null, "pentru umiditate", 40.0D, null);
            sensor_agent0.setDevice(device_agent0);
            sensorsRepository.save(sensor_agent0);

//            Sensors sensor_agent1 = new Sensors(null, "pentru temperatura", 45.0D, null);
//            sensor_agent1.setDevice(device_agent1);
//            sensorsRepository.save(sensor_agent1);


            Measurements measurement_agent0_11_4_5 = new Measurements(null,
                    new Date(2021-1900, 12-1, 9, 15, 0),
                    5D, null);
            measurement_agent0_11_4_5.setSensor(sensor_agent0);
            measurementsRepository.save(measurement_agent0_11_4_5);

            Measurements measurement_agent1_11_4_9 = new Measurements(null,
                    new Date(2021-1900, 12-1, 9, 16, 12),
                    9D, null);
            measurement_agent1_11_4_9.setSensor(sensor_agent0);
            measurementsRepository.save(measurement_agent1_11_4_9);

            Measurements measurement_agent2_11_4_19 = new Measurements(null,
                    new Date(2021-1900, 12-1, 9, 17, 0),
                    15D, null);
            measurement_agent2_11_4_19.setSensor(sensor_agent0);
            measurementsRepository.save(measurement_agent2_11_4_19);

            Measurements measurement_agent3 = new Measurements(null,
                    new Date(2021-1900, 12-1, 9, 18, 0),
                    20D, null);
            measurement_agent3.setSensor(sensor_agent0);
            measurementsRepository.save(measurement_agent3);

            Measurements measurement_agent4 = new Measurements(null,
                    new Date(2021-1900, 12-1, 9, 19, 20),
                    27D, null);
            measurement_agent4.setSensor(sensor_agent0);
            measurementsRepository.save(measurement_agent4);



//            Measurements measurement_agent5= new Measurements(null,
//                    new Date(2021-1900, 12-1, 11, 13, 0),
//                    11D, null);
//            measurement_agent5.setSensor(sensor_agent1);
//            measurementsRepository.save(measurement_agent5);
//
//            Measurements measurement_agent6= new Measurements(null,
//                    new Date(2021-1900, 12-1, 11, 14, 45),
//                    18D, null);
//            measurement_agent6.setSensor(sensor_agent1);
//            measurementsRepository.save(measurement_agent6);
//
//            Measurements measurement_agent7= new Measurements(null,
//                    new Date(2021-1900, 12-1, 11, 15, 7),
//                    30D, null);
//            measurement_agent7.setSensor(sensor_agent1);
//            measurementsRepository.save(measurement_agent7);
//
//            Measurements measurement_agent8= new Measurements(null,
//                    new Date(2021-1900, 12-1, 11, 16, 35),
//                    35D, null);
//            measurement_agent8.setSensor(sensor_agent1);
//            measurementsRepository.save(measurement_agent8);



            Measurements measurement_agent9 = new Measurements(null,
                    new Date(2021-1900, 12-1, 8, 10, 0),
                    12D, null);
            measurement_agent9.setSensor(sensor_agent0);
            measurementsRepository.save(measurement_agent9);
            Measurements measurement_agent10 = new Measurements(null,
                    new Date(2021-1900, 12-1, 8, 11, 0),
                    17D, null);
            measurement_agent10.setSensor(sensor_agent0);
            measurementsRepository.save(measurement_agent10);
            Measurements measurement_agent11 = new Measurements(null,
                    new Date(2021-1900, 12-1, 8, 12, 0),
                    24D, null);
            measurement_agent11.setSensor(sensor_agent0);
            measurementsRepository.save(measurement_agent11);



//            Measurements measurement_agent12 = new Measurements(null,
//                    new Date(2021-1900, 12-1, 9, 9, 0),
//                    3D, null);
//            measurement_agent12.setSensor(sensor_agent1);
//            measurementsRepository.save(measurement_agent12);
//            Measurements measurement_agent13 = new Measurements(null,
//                    new Date(2021-1900, 12-1, 9, 10, 0),
//                    7D, null);
//            measurement_agent13.setSensor(sensor_agent1);
//            measurementsRepository.save(measurement_agent13);
//            Measurements measurement_agent14 = new Measurements(null,
//                    new Date(2021-1900, 12-1, 9, 11, 0),
//                    11D, null);
//            measurement_agent14.setSensor(sensor_agent1);
//            measurementsRepository.save(measurement_agent14);
//            Measurements measurement_agent15 = new Measurements(null,
//                    new Date(2021-1900, 12-1, 9, 12, 0),
//                    16D, null);
//            measurement_agent15.setSensor(sensor_agent1);
//            measurementsRepository.save(measurement_agent15);


            //clientsRepository.findAll().forEach(System.out::println);
            //System.out.println("\n");
            devicesRepository.findAll().forEach(System.out::println);
            System.out.println();
            sensorsRepository.findAll().forEach(System.out::println);
            System.out.println();
            measurementsRepository.findAll().forEach(System.out::println);

            System.out.println("\nDone1\n");

        };
    }

}
