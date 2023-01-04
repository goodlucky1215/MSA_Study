package fashion.userservice.dto;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode
@Setter
@Getter
public class MemberInfoDto {

    private Long pkId;

    @NotBlank(message="닉네임은 2자 이상으로 입력 해주세요.")
    @Size(min = 2,max =10,message="닉네임은 2자 이상 10자 이하여야 합니다.")
    private String nickname;

    private String grade;

}
