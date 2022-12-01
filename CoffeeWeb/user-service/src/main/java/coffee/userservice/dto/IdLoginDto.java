package coffee.userservice.dto;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class IdLoginDto {

    @NotNull
    @Size(min = 2,message="id는 2자 이상이어야 합니다.")
    private String id;

    @NotNull
    @Size(min = 4,message="패스워드는 4자 이상이어야합니다.")
    private String password;

}
