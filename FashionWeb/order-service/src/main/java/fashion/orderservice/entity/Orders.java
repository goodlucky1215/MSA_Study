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

    @OneToMany(mappedBy = "orderitemId")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime orderDate;

    @Builder
    public Orders(Member member, List<OrderItem> orderItem, OrderStatus orderStatus){
        this.member = member;
        this.orderItems.addAll(orderItem);
        this.orderStatus = orderStatus;
        OrderItem.builder().order(this).build();
    }
}
