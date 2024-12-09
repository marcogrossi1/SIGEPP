package com.sigepp.demo;

import org.springframework.boot.SpringApplication;

public class TestSigeppApplication {

	public static void main(String[] args) {
		SpringApplication.from(SigeppApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
