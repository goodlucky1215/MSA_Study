package coffee.userservice.dto;

import com.sun.istack.NotNull;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;

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
    //test 임의로 생성
    public String getGrade() {
        return grade;
    }
}
