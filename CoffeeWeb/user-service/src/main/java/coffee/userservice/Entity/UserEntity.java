package coffee.userservice.Entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@DynamicInsert
@Entity
@Table(name="member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter //test할때만 추가
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkId;

    @Column(unique = true, length = 40)
    private String id;

    @Column(unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 30)
    private String passwordEncrypt;

    private LocalDate birth;

    private String grade;

    private LocalDateTime joinDate;

    @Builder
    public UserEntity(String id, String email, String nickname, String passwordEncrypt, LocalDate birth, String grade, LocalDateTime joinDate){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.passwordEncrypt = passwordEncrypt;
        this.birth = birth;
        this.grade = grade;
        this.joinDate = joinDate;
    }
}
