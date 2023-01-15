package fashion.orderservice.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Orderitem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false, updatable = false,insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDateTime orderDate;

    @Builder
    private Orders(Member member, List<Orderitem> orderItems){
        this.member = member;
        this.orderItems = orderItems;
        this.status = OrderStatus.ORDER;
        for(Orderitem orderitem : orderItems) {
            orderitem.setOrder(this);
        }
    }

    public static Orders createOrder(Member member, List<Orderitem> orderItems){
        return builder()
                .member(member)
                .orderItems(orderItems)
                .build();
    }


}
