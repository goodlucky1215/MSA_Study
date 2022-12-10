package coffee.userservice.service;

import coffee.userservice.Entity.UserEntity;
import coffee.userservice.dto.IdLoginDto;
import coffee.userservice.dto.UserInfoDto;
import coffee.userservice.dto.UserJoinDto;
import coffee.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;

    final private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public boolean userJoin(UserJoinDto memberJoinDto) {
        log.info("memberJoinDto {}",memberJoinDto);
        UserEntity userEntity = mapper.map(memberJoinDto, UserEntity.class);
        boolean extistIdTF = userRepository.existsById(userEntity.getId());
        if(extistIdTF) return false;
        userEntity.builder().password(bCryptPasswordEncoder.encode(userEntity.getPassword()));
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
        return new User(userInfoEntity.getId(),userInfoEntity.getPassword(),
                true, true, true, true,
                new ArrayList<>());
    }

    @Override
    public UserInfoDto userNicknameChange(UserInfoDto userInfoDto) {
        UserEntity userInfoEntity = userRepository.findByPkId(userInfoDto.getPkId());
        userInfoEntity.builder().nickname(userInfoDto.getNickname());
        UserInfoDto returnUserInfoDto = mapper.map(userInfoEntity,UserInfoDto.class);
        return returnUserInfoDto;
    }

}
