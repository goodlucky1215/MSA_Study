package com.example.orderservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDto implements Serializable {
    public String productId;
    public Integer qty;
    public Integer unitPrice;
    public Integer totalPrice;

    private String orderId;
    private String userId;
}
