package com.example.catalogservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatalogDto implements Serializable {
    public String productId;
    public Integer qty;
    public Integer unitPrice;
    public Integer totalPrice;

    private String orderId;
    private String userId;
}
