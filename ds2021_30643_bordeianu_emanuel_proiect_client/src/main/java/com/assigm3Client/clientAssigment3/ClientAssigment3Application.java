package com.assigm3Client.clientAssigment3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootApplication
public class ClientAssigment3Application {

	@Bean
	public HessianProxyFactoryBean hessianInvoker() {
		HessianProxyFactoryBean invoker = new HessianProxyFactoryBean();
		//invoker.setServiceUrl("http://localhost:8080/loginHessian");
		invoker.setServiceUrl("https://spring-emi-backend.herokuapp.com/loginHessian");
		invoker.setServiceInterface(MeasureValue.class);
		System.out.println("invoker2 = " + invoker);
		return invoker;
	}


	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		LocalDateTime todayDateTime = LocalDateTime.now();
		ZonedDateTime currentDateTime = todayDateTime.atZone(ZoneId.of("Europe/London") );
		System.out.println("Time in London::");
		System.out.println(dateTimeFormatter.format(currentDateTime));

		ConfigurableApplicationContext ctx = SpringApplication.run(ClientAssigment3Application.class);
		MeasureValue measureValue = ctx.getBean(MeasureValue.class);
		//MeasureValue measureValue = SpringApplication.run(ClientAssigment3Application.class, args).getBean(MeasureValue.class);
		
		//ConfigurableApplicationContext context =  SpringApplication.run(ClientAssigment3Application.class, args);

		//Double[] dd = measureValue.getAverageEnergConsSubpunct2(3L);
		System.out.println("------- CLIENT -------");
		
		ctx.close();
		
	}


}
