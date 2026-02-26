package ua.com.foxmined.carrestservice;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ua.com.foxmined.carrestservice.utils.CarDBInitializer;

@Log4j2
@SpringBootApplication
public class CarRestServiceApplication {

	@Autowired
	private CarDBInitializer carDBInitializer;

	public static void main(String[] args) {
		SpringApplication.run(CarRestServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			log.info("ApplicationRunner has started");
			carDBInitializer.createRowsInDb();
		};
	}
}
