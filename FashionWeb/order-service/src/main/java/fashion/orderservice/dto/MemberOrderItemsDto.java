package fashion.orderservice.dto;

import com.sun.istack.NotNull;
import fashion.orderservice.entity.Category;
import fashion.orderservice.entity.Seller;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class MemberOrderItemsDto {

    @NotNull
    private Long orderitemId;

    @NotNull
    private Seller seller;

    @NotNull
    private String itemName;

    @NotNull
    private Category category;

    @NotNull
    private Long itemId;

    @NotNull
    private Long orderQuantity;

    @NotNull
    private Long orderPrice;

}
