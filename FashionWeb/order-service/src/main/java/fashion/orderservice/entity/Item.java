package fashion.orderservice.entity;

import fashion.orderservice.exception.NotEnoutStockException;
import lombok.*;

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

    public void removeStock(Long orderQuantity) {
        Long restStock = this.quantity - orderQuantity;
        if(restStock < 0) throw new NotEnoutStockException("재고 수량이 부족합니다.");
        this.quantity = restStock;
    }
}
