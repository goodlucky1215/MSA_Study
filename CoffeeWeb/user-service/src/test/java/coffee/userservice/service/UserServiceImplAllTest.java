package coffee.userservice.service;

import coffee.userservice.Entity.UserEntity;
import coffee.userservice.dto.UserInfoDto;
import coffee.userservice.dto.UserJoinDto;
import coffee.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@Transactional
@Slf4j
class UserServiceImplAllTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ModelMapper mapper;

    @DisplayName("암호화 되는지 확인")
    @Test
    public void bcrypt_success(){
        //given
        String password="1234";

        //when
        String bcPassword = bCryptPasswordEncoder.encode(password);
        log.info("bcPassword : {}",bcPassword);

        //then
        assertEquals(true,bCryptPasswordEncoder.matches(password,bcPassword));
    }

    @DisplayName("id_회원가입_성공")
    @Test
    public void id_join_success(){
        //given
        UserJoinDto userJoinDto1 = new UserJoinDto();
        userJoinDto1.setId("id11");
        userJoinDto1.setPassword("1234");
        userJoinDto1.setNickname("노오란개굴");
        userJoinDto1.setBirth(LocalDate.of(1982, 7, 13));

        //when
        boolean result = userService.userJoin(userJoinDto1);
        log.info(userJoinDto1.getPassword());
        userService.getUserInfo("id11");

        //then
        assertEquals(true,result);
        assertEquals(true,bCryptPasswordEncoder.matches("1234",userJoinDto1.getPassword()));
    }

    @DisplayName("bulider사용시")
    @Test
    public void builder_mapper(){
        //given
        UserJoinDto userJoinDto1 = new UserJoinDto();
        userJoinDto1.setId("id11");
        userJoinDto1.setPassword("1234");
        userJoinDto1.setNickname("노오란개굴");
        userJoinDto1.setBirth(LocalDate.of(1982, 7, 13));
        UserEntity userEntity = mapper.map(userJoinDto1, UserEntity.class);

        //when
        userEntity.builder().nickname("aaa").build();

        //then
        assertEquals("aaa",userEntity.getNickname());
    }

}