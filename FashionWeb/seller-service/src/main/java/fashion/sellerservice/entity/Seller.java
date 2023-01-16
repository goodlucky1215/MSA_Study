package fashion.sellerservice.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sellerId;

    @OneToMany(mappedBy = "itemId")
    private List<Item> items = new ArrayList<>();

    @Column(unique = true, length = 40)
    private String id;

    @Column(nullable = false, length = 20)
    private String companyName;

    @Column(nullable = false, length = 300)
    private String passwordEncrypt;

    @Column(nullable = false, updatable = false,insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDateTime joinDate;

}
