package coffee.userservice.service;

import coffee.userservice.dto.UserJoinDto;
import coffee.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

//@Mock을 사용_단위테스트를 의미_스프링 컨테이너에 등록하지 않고 가짜 객체 생성
@Extensions(MockitoExtension.class)
class UserServiceImplTest {

    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

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
        userJoinDto1.setId("1");
        userJoinDto1.setPassword("1234");
        userJoinDto1.setNickname("노오란개굴");
        userJoinDto1.setBirth(LocalDate.of(1982, 7, 13));
        UserJoinDto userJoinDto2 = new UserJoinDto();
        userJoinDto2.setId("1");
        userJoinDto2.setPassword("1234");
        userJoinDto2.setNickname("노오란개굴");
        userJoinDto2.setBirth(LocalDate.of(1982, 7, 13));
        userService.userJoin(userJoinDto1);
        given

        //when
        boolean result = userService.userJoin(userJoinDto2);

        //then
        assertEquals(false,result);
    }

}