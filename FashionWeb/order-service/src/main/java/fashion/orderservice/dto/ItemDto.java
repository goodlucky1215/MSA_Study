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
public class ItemDto {

    @NotNull
    private Long itemId;
    @NotNull
    private Seller seller;
    @NotNull
    private String itemName;
    @NotNull
    private Long price;
    @NotNull
    private Long quantity;
    @NotNull
    private Category category;

}
