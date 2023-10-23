package com.augusto.address;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AddressConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressConsumerApplication.class, args);
	}

}
