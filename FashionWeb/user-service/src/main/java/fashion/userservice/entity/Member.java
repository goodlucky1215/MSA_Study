package fashion.userservice.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@DynamicInsert
@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter //test할때만 추가
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkId;

    @Column(unique = true, length = 40)
    private String id;

    @Column(unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 30, name = "passwordEncrypt")
    private String password;

    private LocalDate birth;

    private String grade;

    @Column(nullable = false, updatable = false,insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDateTime joinDate;

    @Builder
    public Member(Long pkId, String id, String email, String nickname, String password, LocalDate birth, String grade){
        this.pkId = pkId;
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.birth = birth;
        this.grade = grade;
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    public void changePasswordEncrypt(String passwordEncrypt){
        this.password = passwordEncrypt;
    }

}
