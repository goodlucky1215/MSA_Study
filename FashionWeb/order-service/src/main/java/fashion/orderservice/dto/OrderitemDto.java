package fashion.orderservice.dto;

import com.sun.istack.NotNull;
import fashion.orderservice.entity.Item;
import fashion.orderservice.entity.Orders;

import javax.persistence.*;

public class OrderitemDto {

    @NotNull
    private Long itemId;

    @NotNull
    private Long orderQuantity;

    @NotNull
    private Long orderPrice;

}
