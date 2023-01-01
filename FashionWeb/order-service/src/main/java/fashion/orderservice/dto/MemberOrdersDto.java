package fashion.orderservice.dto;

import fashion.orderservice.entity.Member;
import fashion.orderservice.entity.OrderStatus;
import fashion.orderservice.entity.Orderitem;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MemberOrdersDto {

    private Long orderId;

    private List<MemberOrderItemsDto> orderItems = new ArrayList<>();

    private OrderStatus status;

    private LocalDateTime orderDate;
}
