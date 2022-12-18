package fashion.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@EqualsAndHashCode
@Getter
@Setter //test시에만 개방
public class UserJoinDto {

    @NotBlank(message = "빈 값이 아니어야합니다.")
    @Size(min = 2,message="id는 2자 이상이어야 합니다.")
    private String id;

    @NotBlank(message = "빈 값이 아니어야합니다.")
    @Size(min = 2,max =10,message="닉네임은 2자 이상 10자 이하여야 합니다.")
    private String nickname;

    @NotBlank(message = "빈 값이 아니어야합니다.")
    @Size(min =4, max = 20, message = "패스워드는 4자 이상 20자 이하여야 합니다")
    private String password;

    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate birth;

}
