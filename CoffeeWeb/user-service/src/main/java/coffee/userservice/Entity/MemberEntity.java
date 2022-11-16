package coffee.userservice.Entity;

import coffee.userservice.dto.MemberDto;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name="member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk_id;

    @Column(unique = true, length = 40)
    private String id;

    @Column(unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 30)
    private String password;

    private LocalDate birth;

    @Column(length = 10)
    private String grade;

    private LocalDateTime join_date;

    @Builder
    public MemberEntity(String id,String email,String nickname, String password,LocalDate birth,String grade,LocalDateTime join_date){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.birth = birth;
        this.grade = grade;
        this.join_date = join_date;
    }
}
