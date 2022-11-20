package coffee.userservice.dto;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import java.time.LocalDate;

public class UserInfoDto {

    @NotNull
    private Long pkId;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(length = 10)
    private String grade;

    public Long getPkId() {
        return pkId;
    }
    public String getNickname() {
        return nickname;
    }
}
