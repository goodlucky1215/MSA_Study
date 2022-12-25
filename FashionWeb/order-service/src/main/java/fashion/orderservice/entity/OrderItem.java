package fashion.orderservice.entity;

import lombok.AccessLevel;
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
    Long orderitemId;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    Order order;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    Item item;


}
