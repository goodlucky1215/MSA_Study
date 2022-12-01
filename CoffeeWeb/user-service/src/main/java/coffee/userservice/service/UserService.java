package coffee.userservice.service;

import coffee.userservice.Entity.UserEntity;
import coffee.userservice.dto.UserInfoDto;
import coffee.userservice.dto.UserJoinDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    //회원가입
    public boolean userJoin(UserJoinDto userJoinDto);

    // => 아래 메소드는 spring security에서 로그인시 쓰이는 메소드다. UserDetailsService의 메서드로 일반클래스에서 오버라이드해서 받아온다.
    //public UserDetails loadUserByUsername(String id)

    //로그인 성공후 사용자 정보 가져오기
    public UserInfoDto getUserInfo(String userId);

    //닉네임 변경
    public UserInfoDto userNicknameChange(UserInfoDto userInfoDto);

}
