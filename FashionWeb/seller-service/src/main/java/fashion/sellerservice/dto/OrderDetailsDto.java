package fashion.sellerservice.dto;

import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import fashion.sellerservice.entity.OrderStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@EqualsAndHashCode
public class OrderDetailsDto {

    private Long orderitemId;

    private Long orderQuantity;

    private Long orderPrice;

    private OrderStatus orderStatus;

    private String itemName;

    private LocalDateTime orderDate;

    //memberId
    private String id;
}
