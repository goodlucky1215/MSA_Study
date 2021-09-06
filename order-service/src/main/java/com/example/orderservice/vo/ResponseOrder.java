package com.example.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {
    public String productId;
    public Integer qty;
    public Integer unitPrice;
    public Integer totalPrice;
    public Integer stock;
    private Date createdAt;

    public String orderId;
}
