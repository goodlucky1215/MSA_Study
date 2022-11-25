package coffee.userservice.dto;

import com.sun.istack.NotNull;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter //test시에만 개방
public class UserJoinDto {

    public UserJoinDto(){
        this.join_date = LocalDateTime.now();
    }

    //@NotNull
    //@Size(min = 2,message="id는 2자 이상이어야 합니다.")
    private String id;

    //@NotNull
    private String email;

    @NotNull
    private String nickname;

    @NotNull
    private String password;

    @NotNull
    private LocalDate birth;

    @NotNull
    private String grade;
    
    private LocalDateTime join_date;

    public String getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }

}
