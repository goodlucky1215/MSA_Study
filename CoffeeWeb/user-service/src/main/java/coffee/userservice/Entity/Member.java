package coffee.userservice.Entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Member {

    private Long pk_id;
    private String id;
    private String email;
    private String nickname;
    private String password;
    private LocalDateTime birth;
    private String grade;
    private LocalDateTime join_date;
}
