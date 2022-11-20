package coffee.userservice.service;

import coffee.userservice.Entity.UserEntity;
import coffee.userservice.dto.IdLoginDto;
import coffee.userservice.dto.UserInfoDto;
import coffee.userservice.dto.UserJoinDto;
import coffee.userservice.repository.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    final private UserRepositoryImpl userRepository;

    ModelMapper mapper = new ModelMapper();

    @Override
    public boolean userJoin(UserJoinDto memberJoinDto) {
        boolean extistIdTF = userRepository.existsById(memberJoinDto.getId());
        if(extistIdTF) return false;
        userRepository.save(mapper.map(memberJoinDto, UserEntity.class));
        return true;
    }

    @Override
    public UserInfoDto userLoginId(IdLoginDto idLoginDto) {
        UserEntity userInfoEntity = userRepository.findById(idLoginDto.getId());
        if(userInfoEntity==null) return null;

        return null;
    }

    /*
    @Override
    public UserInfoDto userLoginEmail() {
        UserEntity userInfoEntity = userRepository.findById(idLoginDto.getId());
        if(userInfoEntity==null) return null;

        return null;
    }
     */

    @Override
    public UserInfoDto userNicknameChange(UserInfoDto memberInfoDto) {
        UserEntity userInfoEntity = userRepository.findByPkId(memberInfoDto.getPkId());
        userInfoEntity.builder().nickname(memberInfoDto.getNickname());
        UserInfoDto returnUserInfoDto = mapper.map(userInfoEntity,UserInfoDto.class);
        return returnUserInfoDto;
    }
}
