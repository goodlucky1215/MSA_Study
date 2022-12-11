package coffee.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode
@Setter //test시에만 개방
@Getter
public class UserJoinDto {

    //public UserJoinDto(){
    //    this.join_date = LocalDateTime.now();
    //}

    @NotNull
    @Size(min = 2,message="id는 2자 이상이어야 합니다.")
    private String id;

    @NotNull
    @Size(min = 2,max =10,message="닉네임은 2자 이상 10자 이하여야 합니다.")
    private String nickname;

    @NotNull
    @Size(min =4, max = 20, message = "패스워드는 4자 이상 20자 이하여야 합니다")
    private String password;

    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate birth;

    //public String getId() {return id;}
    //public String getPassword() {return password;}

}
