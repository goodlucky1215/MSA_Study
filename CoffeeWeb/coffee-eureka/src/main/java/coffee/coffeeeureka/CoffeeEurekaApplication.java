package coffee.coffeeeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CoffeeEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeEurekaApplication.class, args);
    }

}
