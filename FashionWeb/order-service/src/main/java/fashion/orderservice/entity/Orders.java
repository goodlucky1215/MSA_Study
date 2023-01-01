package fashion.orderservice.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "pkId")
    private Member member;

    @OneToMany(mappedBy = "orderitemId", cascade = CascadeType.ALL)
    private List<Orderitem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDate;

    @Builder
    private Orders(Member member, List<Orderitem> orderItems, OrderStatus status){
        this.member = member;
        this.orderItems = orderItems;
        this.status = status;
        for(Orderitem orderitem : orderItems) {
            orderitem.setOrder(this);
        }
    }

    public static Orders createOrder(Member member, List<Orderitem> orderItems){
        return builder()
                .member(member)
                .orderItems(orderItems)
                .status(OrderStatus.ORDER)
                .build();
    }


}
