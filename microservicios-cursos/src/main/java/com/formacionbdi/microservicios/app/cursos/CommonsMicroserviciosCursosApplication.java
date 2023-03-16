package com.formacionbdi.microservicios.app.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.formacionbdi.microservicios.app.cursos.models.entity",
"com.formacionbdi.microservicios.commons.examenes.models.entity"})
//@EnableAutoConfiguration( exclude = { DataSourceAutoConfiguration.class })
public class CommonsMicroserviciosCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonsMicroserviciosCursosApplication.class, args);
	}

}
