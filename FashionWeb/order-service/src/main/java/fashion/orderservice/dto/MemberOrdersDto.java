package fashion.orderservice.dto;

import fashion.orderservice.entity.Orderitem;
import fashion.orderservice.entity.Orders;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class MemberOrdersDto {

    private Long orderId;

    private List<MemberOrderItemsDto> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    public MemberOrdersDto(Orders orders){
        orderId = orders.getOrderId();
        for(Orderitem item : orders.getOrderItems()){
            MemberOrderItemsDto memberOrderItemsDto = new MemberOrderItemsDto();
            memberOrderItemsDto.setOrderitemId(item.getOrderitemId());
            memberOrderItemsDto.setItemId(item.getItem().getItemId());
            memberOrderItemsDto.setItemName(item.getItem().getItemName());
            memberOrderItemsDto.setOrderQuantity(item.getOrderQuantity());
            memberOrderItemsDto.setOrderPrice(item.getOrderPrice());
            memberOrderItemsDto.setOrderStatus(item.getOrderStatus());
            orderItems.add(memberOrderItemsDto);
        }
        orderDate = orders.getOrderDate();
    }
}
