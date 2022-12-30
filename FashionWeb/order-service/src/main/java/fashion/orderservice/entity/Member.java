package fashion.orderservice.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

    private LocalDateTime joinDate;

    @Builder
    private Member(String id, String email, String nickname, String password, LocalDate birth, String grade, LocalDateTime joinDate){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.birth = birth;
        this.grade = grade;
        this.joinDate = joinDate;
    }

}
