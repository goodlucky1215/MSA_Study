package coffee.userservice.controller;

import coffee.userservice.dto.UserInfoDto;
import coffee.userservice.dto.UserJoinDto;
import coffee.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;

    @GetMapping("hello")
    public String hello(){
        return "hello userservice";
    }

    @PostMapping("/join")
    public String join(@RequestBody UserJoinDto userJoinDto){
        if(userService.userJoin(userJoinDto)) return "회원가입 성공";
        return "회원가입 실패";
    }


    @PostMapping("/nicknamechange")
    public String nickNameChange(@RequestBody UserInfoDto userInfoDto){
        UserInfoDto changeUserInfoDto = userService.userNicknameChange(userInfoDto);
        return "정보변경 성공";
    }


}
