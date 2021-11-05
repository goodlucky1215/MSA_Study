package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order-service")
public class OrderController {
    Environment env;
    OrderService orderService;
    KafkaProducer kafkaProducer;

    @Autowired
    public OrderController(Environment env, OrderService orderService,KafkaProducer kafkaProducer){
        this.env = env;
        this.orderService = orderService;
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/health_check")
    public String status(){
        return String.format("It's Working in Catalog Service on Port %s",
                env.getProperty("local.server.port"));
    }

    //http://127.0.0.1:0/order-service/{user_id}/orders
    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(
            @PathVariable("userId") String userId
            ,@RequestBody RequestOrder orderDetail){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(orderDetail, OrderDto.class);
        orderDto.setUserId(userId);
        /* 1. jpa */
        //OrderDto createdOrder = orderService.createOrder(orderDto);
        //ResponseOrder responseUser = mapper.map(createdOrder, ResponseOrder.class);

        /* 2. kafka  --> orderService.createOrder에 있는거 가져온 거임 */
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDetail.getQty()*orderDetail.getUnitPrice());

        /* send this order to the kafka */
        kafkaProducer.send("example-catalog-topic", orderDto);

        /* 2-1. */
        ResponseOrder responseUser = mapper.map(orderDto, ResponseOrder.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> createOrder(@PathVariable("userId") String userId)
    {
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
