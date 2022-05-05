package arv.springdockerrabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * перед запуском приложения запустим раббит в докере
 * docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
 */
@SpringBootApplication
public class SpringDockerRabbitmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDockerRabbitmqApplication.class, args);
	}

}
