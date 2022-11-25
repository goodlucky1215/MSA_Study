package coffee.userservice.dto;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import java.time.LocalDate;

public class UserInfoDto {

    @NotNull
    private Long pkId;

    @NotNull
    //@Size(min = 2,message="id는 2자 이상이어야 합니다.")
    private String nickname;

    @NotNull
    private String grade;

    public Long getPkId() {
        return pkId;
    }
    public String getNickname() {
        return nickname;
    }
}
