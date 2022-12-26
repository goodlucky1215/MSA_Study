package fashion.orderservice.entity;

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
    @JoinColumn(name = "SELLER_ID")
    private Seller seller;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long quantity;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder(toBuilder = true)
    public Item(Long itemId, Long price, Long quantity,Category category){
        this.itemId = itemId;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }



}
