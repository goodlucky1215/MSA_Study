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
public class OrderItem {

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
    public OrderItem(Orders order, Item item, Long orderQuantity){
        this.order = order;
        this.item = item;
        this.orderQuantity = orderQuantity;
        this.orderPrice = orderQuantity*item.getPrice();
    }

}
