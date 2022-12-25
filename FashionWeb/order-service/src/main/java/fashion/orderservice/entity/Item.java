package fashion.orderservice.entity;

import lombok.AccessLevel;
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
    Seller seller;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    Category category;

    private Long price;
    private Long quantity;


}
