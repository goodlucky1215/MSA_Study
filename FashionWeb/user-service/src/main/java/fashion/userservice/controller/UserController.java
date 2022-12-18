package fashion.userservice.controller;

import fashion.userservice.dto.UserInfoDto;
import fashion.userservice.dto.UserJoinDto;
import fashion.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class UserController {

    final private UserService userService;

    @GetMapping("/hello")
    public String hello(){
        return "hello userservice";
    }

    @PostMapping("/join")
    public String join(@Validated @RequestBody UserJoinDto userJoinDto){
        if(userService.userJoin(userJoinDto)) return "true";
        return "false";
    }

    @GetMapping("/userInfo")
    public UserInfoDto userInfo(HttpServletRequest request){
        UserInfoDto userInfoDto = userService.getUserInfo(request.getHeader("userId"));
        return userInfoDto;
    }

    @PostMapping("/nicknamechange")
    public UserInfoDto nickNameChange(@Validated @RequestBody UserInfoDto userInfoDto){
        UserInfoDto changeUserInfoDto = userService.userNicknameChange(userInfoDto);
        return changeUserInfoDto;
    }


}
