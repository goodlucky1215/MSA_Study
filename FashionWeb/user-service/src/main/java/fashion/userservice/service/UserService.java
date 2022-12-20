package fashion.userservice.service;

import fashion.userservice.dto.MemberInfoDto;
import fashion.userservice.dto.MemberJoinDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    //회원가입
    boolean userJoin(MemberJoinDto userJoinDto);

    // => 아래 메소드는 spring security에서 로그인시 쓰이는 메소드다. UserDetailsService의 메서드로 일반클래스에서 오버라이드해서 받아온다.
    //public UserDetails loadUserByUsername(String id)

    //로그인 성공후 사용자 정보 가져오기
    MemberInfoDto getUserInfo(String userId);

    //닉네임 변경
    MemberInfoDto userNicknameChange(MemberInfoDto memberInfoDto);

}
