package com.formacionbdi.microservicios.app.zuul;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class MicroserviciosZuulApplication {

	public static void main(String[] args) {
		/*
		 SpringApplication app = new SpringApplication(MicroserviciosZuulApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8090"));
        app.run(args);
		*/
		SpringApplication.run(MicroserviciosZuulApplication.class, args);
	}

}
