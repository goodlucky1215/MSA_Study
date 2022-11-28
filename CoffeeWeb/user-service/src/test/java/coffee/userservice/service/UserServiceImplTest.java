package coffee.userservice.service;

import coffee.userservice.Entity.UserEntity;
import coffee.userservice.dto.UserInfoDto;
import coffee.userservice.dto.UserJoinDto;
import coffee.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//@Mock을 사용_단위테스트를 의미_스프링 컨테이너에 등록하지 않고 가짜 객체 생성
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository,bCryptPasswordEncoder);
    }

    @DisplayName("id_회원가입_실패_이미존재하는아이디")
    @Test
    public void id_join_fail(){
        //given
        UserJoinDto userJoinDto1 = new UserJoinDto();
        userJoinDto1.setId("id1");
        userJoinDto1.setPassword("1234");
        userJoinDto1.setNickname("노오란개굴");
        userJoinDto1.setBirth(LocalDate.of(1982, 7, 13));
        when(userRepository.existsById("id1")).thenReturn(true);


        //when
        boolean result = userService.userJoin(userJoinDto1);

        //then
        assertEquals(false,result);
    }

    @DisplayName("id_회원가입_성공")
    @Test
    public void id_join_success(){
        //given
        UserJoinDto userJoinDto1 = new UserJoinDto();
        userJoinDto1.setId("id1");
        userJoinDto1.setPassword("1234");
        userJoinDto1.setNickname("노오란개굴");
        userJoinDto1.setBirth(LocalDate.of(1982, 7, 13));
        when(userRepository.existsById("id1")).thenReturn(false);


        //when
        boolean result = userService.userJoin(userJoinDto1);

        //then
        assertEquals(true,result);
    }


    @DisplayName("id_로그인_실패_id가 존재하지 않는다")
    @Test
    public void id_login_fail(){
        //given
        when(userRepository.findById("id1")).thenReturn(null);


        //when
        UserDetails result = userService.loadUserByUsername("id1");

        //then
        assertEquals(null,result);
    }

    @DisplayName("id_로그인_성공")
    @Test
    public void id_login_success(){
        //given
        UserEntity memberEntity = UserEntity.builder()
                .id("id1")
                .nickname("아프로디테")
                .passwordEncrypt("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        when(userRepository.findById("id1")).thenReturn(memberEntity);


        //when
        UserDetails result = userService.loadUserByUsername("id1");

        //then
        assertEquals(new User(memberEntity.getId(),memberEntity.getPasswordEncrypt(),
                true, true, true, true,
                new ArrayList<>()),result);
    }

    
    //mapper왜 안되는지 확인하기
    @DisplayName("닉네임_변경_성공")
    @Test
    public void nickNameChange_success(){
        //given
        UserEntity memberEntity = UserEntity.builder()
                .pkId(1L)
                .id("id1")
                .nickname("아프로디테")
                .passwordEncrypt("1234")
                .joinDate(LocalDateTime.now())
                .birth(LocalDate.of(1982, 7, 13))
                .build();
        when(userRepository.findByPkId(1L)).thenReturn(memberEntity);


        //when
        UserEntity returnUserInfoDto = userService.userNicknameChange(1L,"헤라");

        //then
        System.out.println(returnUserInfoDto.getGrade());
        //assertEquals("헤라",userInfoDto.getNickname());
        assertEquals(1L,returnUserInfoDto.getPkId());
    }

}