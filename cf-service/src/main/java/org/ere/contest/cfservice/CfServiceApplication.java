package org.ere.contest.cfservice;

import org.ere.contest.orderstarter.config.MetricsRegistry;
import org.ere.contest.orderstarter.web.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({MetricsRegistry.class, GlobalExceptionHandler.class})
public class CfServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CfServiceApplication.class, args);
	}

}
