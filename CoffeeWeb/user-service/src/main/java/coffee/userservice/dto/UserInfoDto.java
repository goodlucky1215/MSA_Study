package coffee.userservice.dto;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Size;

@EqualsAndHashCode
@Setter
@Getter
public class UserInfoDto {

    //test 임의로 생성
    public UserInfoDto(){}
    //test 임의로 생성
    public UserInfoDto(Long pkId,String nickname,String grade){
        this.pkId = pkId;
        this.nickname = nickname;
    }


    @NotNull
    private Long pkId;

    @NotNull
    @Size(min = 2,max =10,message="닉네임은 2자 이상 10자 이하여야 합니다.")
    private String nickname;

    private String grade;

}
