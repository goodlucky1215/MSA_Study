package fashion.sellerservice.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sellerId;

    @OneToMany(mappedBy = "seller")
    private final List<Item> items = new ArrayList<>();

    @Column(unique = true, length = 40)
    private String id;

    @Column(nullable = false, length = 20)
    private String companyName;

    @Column(nullable = false, length = 300)
    private String passwordEncrypt;

    @Column(nullable = false, updatable = false,insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDateTime joinDate;

    @Builder
    private Seller(String id, String companyName, String passwordEncrypt){
        this.id = id;
        this.companyName = companyName;
        this.passwordEncrypt = passwordEncrypt;
    }

    public void changePasswordEncrypt(String passwordEncrypt){
        this.passwordEncrypt = passwordEncrypt;
    }

}
