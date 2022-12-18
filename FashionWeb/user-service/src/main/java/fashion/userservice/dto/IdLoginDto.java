package fashion.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class IdLoginDto {

    @NotBlank(message = "빈 값이 아니어야합니다.")
    @Size(min = 2,message="id는 2자 이상이어야 합니다.")
    private String id;

    @NotBlank(message = "빈 값이 아니어야합니다.")
    @Size(min = 4,message="패스워드는 4자 이상이어야합니다.")
    private String password;

}
