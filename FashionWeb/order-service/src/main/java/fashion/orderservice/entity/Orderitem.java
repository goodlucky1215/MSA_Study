package fashion.orderservice.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Orderitem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderitemId;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;

    @Column(nullable = false)
    private Long orderQuantity;

    @Column(nullable = false)
    private Long orderPrice;

    @Builder
    private Orderitem(Orders order, Item item, Long orderQuantity, Long orderPrice){
        this.order = order;
        this.item = item;
        this.orderQuantity = orderQuantity;
        this.orderPrice = orderPrice;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public static Orderitem createOrderitem(Item item, Long orderQuantity){
        item.removeStock(orderQuantity);
        return builder()
                .item(item)
                .orderQuantity(orderQuantity)
                .orderPrice(orderQuantity* item.getPrice())
                .build();
    }
}
