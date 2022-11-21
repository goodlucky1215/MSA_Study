package coffee.userservice.dto;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserJoinDto {

    public UserJoinDto(){
        this.join_date = LocalDateTime.now();
    }

    @NotNull
    //@Size(min = 2,message="id는 2자 이상이어야 합니다.")
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

    public String getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }

}
