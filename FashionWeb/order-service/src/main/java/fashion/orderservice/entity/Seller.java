package fashion.orderservice.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sellerId;

    @Column(unique = true, length = 40)
    private String id;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 300)
    private String passwordEncrypt;

    private LocalDateTime joinDate;

    @Builder(toBuilder = true)
    public Seller(Long sellerId, String id, String nickname, String passwordEncrypt, LocalDateTime joinDate){
        this.sellerId = sellerId;
        this.id = id;
        this.nickname = nickname;
        this.passwordEncrypt = passwordEncrypt;
        this.joinDate = joinDate;
    }

}
