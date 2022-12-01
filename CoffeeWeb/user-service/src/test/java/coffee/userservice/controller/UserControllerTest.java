package coffee.userservice.controller;

import coffee.userservice.dto.UserJoinDto;
import coffee.userservice.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

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
        Gson gson = new Gson();
        given(userService.userJoin(userJoinDto)).willReturn(false);

        //when, then
        mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .accept(gson.toJson(userJoinDto)))
                .andReturn();
                //.andExpect(content().string("회원가입 실패"));

        boolean a = verify(userService).userJoin(userJoinDto);
        System.out.println("Sdfsdfsd => "+ a);
    }



}