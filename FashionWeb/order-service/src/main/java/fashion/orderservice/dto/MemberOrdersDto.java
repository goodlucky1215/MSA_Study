package fashion.orderservice.dto;

import fashion.orderservice.entity.OrderStatus;
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

    private OrderStatus status;

    private LocalDateTime orderDate;
}
