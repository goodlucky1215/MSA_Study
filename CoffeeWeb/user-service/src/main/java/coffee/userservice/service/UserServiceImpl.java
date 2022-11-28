package coffee.userservice.service;

import coffee.userservice.Entity.UserEntity;
import coffee.userservice.dto.IdLoginDto;
import coffee.userservice.dto.UserInfoDto;
import coffee.userservice.dto.UserJoinDto;
import coffee.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;

    final private BCryptPasswordEncoder bCryptPasswordEncoder;

    ModelMapper mapper = new ModelMapper();

    @Override
    public boolean userJoin(UserJoinDto memberJoinDto) {
        boolean extistIdTF = userRepository.existsById(memberJoinDto.getId());
        if(extistIdTF) return false;
        UserEntity userEntity = mapper.map(memberJoinDto, UserEntity.class);
        userEntity.builder().passwordEncrypt(bCryptPasswordEncoder.encode(memberJoinDto.getPassword()));
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public UserInfoDto getUserInfo(String userId) {
        UserEntity userInfoEntity = userRepository.findById(userId);
        UserInfoDto returnUserInfoDto = mapper.map(userInfoEntity,UserInfoDto.class);
        return returnUserInfoDto;
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
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserEntity userInfoEntity = userRepository.findById(id);
        if(userInfoEntity==null) return null;
        return new User(userInfoEntity.getId(),userInfoEntity.getPasswordEncrypt(),
                true, true, true, true,
                new ArrayList<>());
    }

    @Override
    public UserEntity userNicknameChange(Long pkid,String changeNickname) {
        UserEntity userInfoEntity = userRepository.findByPkId(pkid);
        userInfoEntity.builder().nickname(changeNickname);
        //UserInfoDto returnUserInfoDto = mapper.map(userInfoEntity,UserInfoDto.class);
        return userInfoEntity;
    }

}
