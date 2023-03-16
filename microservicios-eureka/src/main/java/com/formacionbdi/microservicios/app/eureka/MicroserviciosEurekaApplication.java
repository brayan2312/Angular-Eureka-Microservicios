package com.formacionbdi.microservicios.app.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MicroserviciosEurekaApplication {

	public static void main(String[] args) {
		System.setProperty("server.port", "8761");
		SpringApplication.run(MicroserviciosEurekaApplication.class, args);
	}

}
