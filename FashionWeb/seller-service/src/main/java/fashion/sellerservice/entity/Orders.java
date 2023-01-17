package fashion.sellerservice.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false, updatable = false,insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDateTime orderDate;

    @Builder
    private Orders(Member member, List<Orderitem> orderItems){
        this.member = member;
        this.orderItems = orderItems;
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
