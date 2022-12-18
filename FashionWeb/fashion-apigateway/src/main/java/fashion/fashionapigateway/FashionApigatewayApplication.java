package fashion.fashionapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
class FashionApigatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FashionApigatewayApplication.class, args);
    }

}
