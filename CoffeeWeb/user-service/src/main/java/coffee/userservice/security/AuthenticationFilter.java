package coffee.userservice.security;

import coffee.userservice.dto.IdLoginDto;
import coffee.userservice.dto.UserInfoDto;
import coffee.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//login구현
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final Environment env;

    //로그인 처리 메소드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            IdLoginDto creds = new ObjectMapper().readValue(request.getInputStream(), IdLoginDto.class);

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
        UserInfoDto userInfoDto = userService.getUserInfo(userId);

        String token = Jwts.builder()
                .setSubject(userInfoDto.getPkId().toString())
                .setExpiration(new Date(System.currentTimeMillis()+
                        Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

        response.addHeader("token",token);
        Map map = new HashMap();
        map.put("result",true);
        map.put("user",userInfoDto);
        response.getWriter().write(new ObjectMapper().writeValueAsString(map));
    }

    //로그인 실패 시 작동 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        request.setAttribute("loginFailMsg", "로그인에 실패하였습니다.");

        // 로그인 페이지로 다시 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
        dispatcher.forward(request, response);
    }
}
