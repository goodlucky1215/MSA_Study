package fashion.orderservice.dto;

import com.sun.istack.NotNull;
import fashion.orderservice.entity.Category;
import fashion.orderservice.entity.Seller;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class MemberOrderItemsDto {

    @NotNull
    private Long orderitemId;

    @NotNull
    private String itemName;

    @NotNull
    private Long itemId;

    @NotNull
    private Long orderQuantity;

    @NotNull
    private Long orderPrice;

}
