package coffee.orderservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-service")
public class CoffeeOrderController {

    @GetMapping("hello")
    public String hello(){
        return "hello orderservice";
    }
}
