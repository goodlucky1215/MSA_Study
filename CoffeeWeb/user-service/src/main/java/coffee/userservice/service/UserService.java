package coffee.userservice.service;

import coffee.userservice.Entity.MemberEntity;
import coffee.userservice.dto.IdLoginDto;
import coffee.userservice.dto.MemberDto;
import coffee.userservice.dto.UserInfoDto;
import coffee.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceImpl{

    final private UserRepository userRepository;

    @Override
    public MemberDto userLoginId(IdLoginDto idLoginDto) {
        //MemberEntity userInfo = userRepository.findById();
        return null;
    }

    @Override
    public MemberDto userLoginEmail() {
        return null;
    }

    @Override
    public MemberDto userNicknameChange(UserInfoDto userInfoDto) {
        return null;
    }
}
