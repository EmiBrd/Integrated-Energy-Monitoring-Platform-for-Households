package com.example.producertema2;

import com.example.producertema2.configure.RabbitMQConfig;
import com.example.producertema2.model.MeasurementAssignment2;
import com.example.producertema2.reader.SensorReader;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
public class ProducerTema2Application {

	@Autowired
    private RabbitTemplate rabbitTemplate;

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		LocalDateTime todayDateTime = LocalDateTime.now();
		ZonedDateTime currentDateTime = todayDateTime.atZone(ZoneId.of("Europe/London") );
		System.out.println("Time in London::");
		System.out.println(dateTimeFormatter.format(currentDateTime));

		SpringApplication.run(ProducerTema2Application.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return args ->
		{

			System.out.println("Inainte de a introduce vreun argument din linia de comanda");
			if(args.length > 0){
				LocalDateTime localDateTime = LocalDateTime.now();
				Date date = new Date(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(),
						localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
				System.out.println("\ndate in main = " + date);

				System.out.println("\nDone\n");

				SensorReader sensorReader = new SensorReader();
				MeasurementAssignment2 measurementAssignment2 =
						new MeasurementAssignment2(Long.parseLong(args[0]), LocalDateTime.now(), 3.0D);

				for(MeasurementAssignment2 m: sensorReader.getMeasurements(
						//32L
						Long.parseLong(args[0])))
				{
					try {
						measurementAssignment2.setSensorId(m.getSensorId());
						measurementAssignment2.setTimestamp(m.getTimestamp());
						measurementAssignment2.setMeasurementValue(m.getMeasurementValue());
						System.out.println("measurement value from sensor.csv = " + m.getMeasurementValue());
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try{
						rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY,
								measurementAssignment2);
					}
					catch (Exception e){
						e.printStackTrace();
					}
				}

			}
			else{
				System.out.println("\nNu ati introdus niciun argument din linia de comanda\n");
			}


		};
	}

}
