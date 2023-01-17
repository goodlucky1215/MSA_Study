package fashion.sellerservice.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "sellerId")
    private Seller seller;

    @Column(nullable = false,length = 100)
    private String itemName;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long quantity;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    private Item(Seller seller, String itemName, Long price, Long quantity, Category category){
        this.seller = seller;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public void removeStock(Long orderQuantity) {
        Long restStock = this.quantity - orderQuantity;
        this.quantity = restStock;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }


}
