package fashion.orderservice.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PK_ID")
    Member member;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    LocalDateTime orderDate;

}
