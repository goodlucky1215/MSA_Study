package com.example.orderservice.messagequeue;


import com.example.orderservice.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class OrderProducer {
    private KafkaTemplate<String,String> kafkaTemplate;

    List<Field> fields = Arrays.asList(new Field("String",true,"order_id"),
            new Field("String",true,"user_id"),
            new Field("String",true,"product_id"),
            new Field("int32",true,"qty"),
            new Field("int32",true,"unit_price"),
            new Field("int32",true,"total_price"));
    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("orders")
            .build();


    @Autowired
    public OrderProducer(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public OrderDto send(String topic, OrderDto orderDto){
        /* kafka랑 관련된 부분임 */
        Payload payload = Payload.builder()
                .order_id(orderDto.getOrderId())
                .user_id(orderDto.getUserId())
                .product_id(orderDto.getProductId())
                .qty(orderDto.getQty())
                .unit_price(orderDto.getUnitPrice())
                .total_price(orderDto.getTotalPrice())
                .build();
        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try{
            //jsonInString = mapper.writeValueAsString(orderDto);
            /* kafka랑 관련된 부분임 */
            jsonInString = mapper.writeValueAsString(kafkaOrderDto);
        }catch (JsonProcessingException ex){
            ex.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonInString);
        //log.info("Kafka Producer send data from the Order microservice : "+orderDto);
        /* kafka랑 관련된 부분임 */
        log.info("Order Producer send data from the Order microservice : "+kafkaOrderDto);

        return orderDto;
    }
}
