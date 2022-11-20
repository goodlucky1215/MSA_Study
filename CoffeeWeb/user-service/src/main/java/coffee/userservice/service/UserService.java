package coffee.userservice.service;

import coffee.userservice.dto.IdLoginDto;
import coffee.userservice.dto.UserInfoDto;
import coffee.userservice.dto.UserJoinDto;

public interface UserService{

    public boolean userJoin(UserJoinDto memberJoinDto);

    public UserInfoDto userLoginId(IdLoginDto idLoginDto);

    //public UserInfoDto userLoginEmail();

    public UserInfoDto userNicknameChange(UserInfoDto memberInfoDto);

}
