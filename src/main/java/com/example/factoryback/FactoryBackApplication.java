package com.example.factoryback;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
public class FactoryBackApplication {

	public static void main(String[] args) {
		// SpringApplication.run(FactoryBackApplication.class, args);

		Robot r1 = new Robot();
		Robot r2 = new Robot();

		r1.start();
		r2.start();
	}

}
