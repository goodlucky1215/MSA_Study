package coffee.userservice.service;

import coffee.userservice.Entity.MemberEntity;
import coffee.userservice.dto.IdLoginDto;
import coffee.userservice.dto.MemberDto;
import coffee.userservice.dto.UserInfoDto;

public interface UserServiceImpl {

    public MemberDto userLoginId(IdLoginDto idLoginDto);

    public MemberDto userLoginEmail();

    public MemberDto userNicknameChange(UserInfoDto userInfoDto);

}
