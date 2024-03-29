package com.example.catalogservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCatalog {
    public String productId;
    public String productName;
    public Integer unitPrice;
    public Integer stock;
    private Date createdAt;
}
