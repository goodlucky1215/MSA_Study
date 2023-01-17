package fashion.sellerservice.dto;

import fashion.sellerservice.entity.Category;
import fashion.sellerservice.entity.Item;
import fashion.sellerservice.entity.Seller;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@EqualsAndHashCode
public class SellerItemListDto {

    private Long itemId;

    private String itemName;

    private Long price;

    private Long quantity;

    private Category category;

}
