package fashion.userservice.security;

import fashion.userservice.common.ErrorResult;
import fashion.userservice.common.Result;
import fashion.userservice.common.ResultCode;
import fashion.userservice.dto.IdLoginDto;
import fashion.userservice.dto.MemberInfoDto;
import fashion.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.validation.annotation.Validated;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//login구현
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final Environment env;

    //로그인 처리 메소드
    @Override
    public Authentication attemptAuthentication(@Validated HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            IdLoginDto creds = new ObjectMapper().readValue(request.getInputStream(), IdLoginDto.class);
            if(StringUtils.isBlank(creds.getId()) || StringUtils.isBlank(creds.getPassword())) {
                throw new AuthenticationServiceException("빈 값이 아니어야합니다.");
            }
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getId(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    //로그인 성공 시 작동 메소드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String userId = ((User)authResult.getPrincipal()).getUsername();
        MemberInfoDto memberInfoDto = userService.getUserInfo(userId);

        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis()+
                        Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

        response.addHeader("token",token);
        response.addHeader("pkId",memberInfoDto.getPkId().toString());
        Map map = new HashMap();
        map.put("result",true);
        Result<Map> result = new Result(map,ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }

    //로그인 실패 시 작동 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        if(failed.getClass().isAssignableFrom(BadCredentialsException.class)) {
            response.getWriter().write(new ObjectMapper().writeValueAsString(new ErrorResult(ResultCode.ERROR.getCode(), "회원 정보를 확인해주세요!")));
        }
        else response.getWriter().write(new ObjectMapper().writeValueAsString(new ErrorResult(ResultCode.ERROR.getCode(),failed.getMessage())));
    }
}
