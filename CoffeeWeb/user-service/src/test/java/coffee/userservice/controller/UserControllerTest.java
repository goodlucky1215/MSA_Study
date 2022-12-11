package coffee.userservice.controller;

import coffee.userservice.Entity.UserEntity;
import coffee.userservice.dto.IdLoginDto;
import coffee.userservice.dto.UserInfoDto;
import coffee.userservice.dto.UserJoinDto;
import coffee.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.HttpServletResponse;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

   // @Autowired
    Environment env;

    @MockBean
    UserService userService;

    @DisplayName("회원가입_실패_아이디중복")
    @Test
    public void join_false_sameId() throws Exception {
        //given
        UserJoinDto userJoinDto = new UserJoinDto();
        userJoinDto.setId("id1");
        userJoinDto.setNickname("병아리");
        userJoinDto.setPassword("123456");
        given(userService.userJoin(userJoinDto)).willReturn(false);

        //when, then
        mockMvc.perform(post("/user-service/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userJoinDto)))
                .andDo(print())
                .andExpect(content().string("회원가입 실패"));

        //이 외로도 BDD 기본 패턴의 then에서 사용되는 Mockito에서 제공하는 verify() 도 then().should() 로 대체될 수 있다.
        //verify(userService, times(1)).userJoin(userJoinDto);
        then(userService).should().userJoin(userJoinDto);
    }

    @DisplayName("회원가입_성공")
    @Test
    public void join_success() throws Exception {
        //given
        UserJoinDto userJoinDto = new UserJoinDto();
        userJoinDto.setId("id1");
        userJoinDto.setNickname("병아리");
        userJoinDto.setPassword("123456");
        given(userService.userJoin(userJoinDto)).willReturn(true);

        //when
        ResultActions resultActions = mockMvc.perform(post("/user-service/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userJoinDto)));

        //then
        resultActions.andDo(print())
                .andExpect(content().string("회원가입 성공"));
        then(userService).should().userJoin(userJoinDto);
    }


    @DisplayName("아이디변경_성공")
    @Test
    public void nickNameChange_success() throws Exception {
        //given
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setPkId(1L);
        userInfoDto.setNickname("병아리");
        given(userService.userNicknameChange(userInfoDto)).willReturn(userInfoDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/user-service/nicknamechange")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userInfoDto)));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("pkId",is(1)))
                .andExpect(jsonPath("nickname",is("병아리")));
        then(userService).should().userNicknameChange(userInfoDto);
    }

    @DisplayName("로그인_성공")
    @Test
    public void login_success() throws Exception {
        //given
        IdLoginDto idLoginDto = new IdLoginDto();
        idLoginDto.setId("id1");
        idLoginDto.setPassword("1234");
        UserEntity userInfoEntity = UserEntity.builder()
                                    .id("id1")
                                    .password(new BCryptPasswordEncoder().encode("1234"))
                                    .build();
        given(userService.loadUserByUsername(idLoginDto.getId())).willReturn(new User(userInfoEntity.getId(),userInfoEntity.getPassword(),
                true, true, true, true,
                new ArrayList<>()));
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setPkId(1L);
        userInfoDto.setNickname("병아리");
        given(userService.getUserInfo("id1")).willReturn(userInfoDto);

        //when
        ResultActions resultActions = mockMvc.perform(post("/login")
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                .content(new ObjectMapper().writeValueAsString(idLoginDto)));

        //then
        resultActions.andDo(print())
                .andExpect(jsonPath("pkId",is(1)))
                .andExpect(jsonPath("nickname",is("병아리")));
        then(userService).should().loadUserByUsername(idLoginDto.getId());
        then(userService).should().getUserInfo("id1");
    }

    @DisplayName("로그인_실패_아이디존재하지않음")
    @Test
    public void login_fail_idIsFalse() throws Exception {
        //given
        IdLoginDto idLoginDto = new IdLoginDto();
        idLoginDto.setId("id1");
        idLoginDto.setPassword("1234");
        UserEntity userInfoEntity = UserEntity.builder()
                                    .id("id1")
                                    .password(new BCryptPasswordEncoder().encode("1234"))
                                    .build();
        given(userService.loadUserByUsername(idLoginDto.getId())).willReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(post("/login")
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                .content(new ObjectMapper().writeValueAsString(idLoginDto)));

        //then
        resultActions.andDo(print())
                .andExpect(forwardedUrl("/login"))
                .andExpect(request().attribute("loginFailMsg", "로그인에 실패하였습니다."));
        then(userService).should().loadUserByUsername(idLoginDto.getId());
    }

}