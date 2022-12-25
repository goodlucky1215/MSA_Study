package fashion.orderservice.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "PK_ID")
    Member member;

    @OneToMany(mappedBy = "ORDERITEM_ID")
    List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    LocalDateTime orderDate;

}
