package coffee.userservice.dto;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserInfoDto {

    @NotNull
    //@Size(min = 2,message="id는 2자 이상이어야 합니다.")
    private String id;

    @Column(unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 20)
    private String nickname;

    private LocalDate birth;

}
