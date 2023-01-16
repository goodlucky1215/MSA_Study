package fashion.orderservice.dto;

import fashion.orderservice.entity.Category;
import fashion.orderservice.entity.Item;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class ItemDto {

    private Long itemId;
    private String companyName;
    private String itemName;
    private Long price;
    private Long quantity;
    private Category category;

    public ItemDto(Item item){
        itemId = item.getItemId();
        companyName = item.getSeller().getCompanyName();
        itemName = item.getItemName();
        price = item.getPrice();
        quantity = item.getQuantity();
        category = item.getCategory();
    }

}
