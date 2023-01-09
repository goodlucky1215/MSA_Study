package fashion.orderservice.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private LocalDateTime joinDate;

    @Builder
    private Seller(String id, String companyName, String passwordEncrypt, LocalDateTime joinDate){
        this.id = id;
        this.companyName = companyName;
        this.passwordEncrypt = passwordEncrypt;
        this.joinDate = joinDate;
    }

}
