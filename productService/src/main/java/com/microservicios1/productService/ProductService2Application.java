package com.microservicios1.productService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProductService2Application {

	public static void main(String[] args) {
		SpringApplication.run(ProductService2Application.class, args);
	}

}
