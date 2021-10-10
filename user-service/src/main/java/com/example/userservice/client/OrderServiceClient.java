package com.example.userservice.client;

import com.example.userservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="order-service") //name="order-service"은 microservice name을 명시해야함
public interface OrderServiceClient {

    @GetMapping("/order-service/{userId}/orders_ng") //0rder-service에서 가져올 path경로를 넣어주면 됨.
    List<ResponseOrder> getOrders(@PathVariable String userId); //path의 가변 변수(유저의 아이디)
}
