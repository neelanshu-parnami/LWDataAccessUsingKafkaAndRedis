package com.metacube.poc.kafkaredispoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@Configuration
//@EnableAutoConfiguration
//@EnableConfigurationProperties
@EnableJpaRepositories(basePackages = "com.metacube.poc.kafkaredispoc.rest.repository")
@SpringBootApplication
public class KafkaRedisPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaRedisPocApplication.class, args);
		/*try {
			new KafkaConsumerService().runConsumer();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}