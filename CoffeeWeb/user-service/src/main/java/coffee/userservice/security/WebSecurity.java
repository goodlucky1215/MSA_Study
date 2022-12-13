package coffee.userservice.security;

import coffee.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final Environment env;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    //권한과 관련된 메소드
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/**").permitAll(); //인중절차없이 리소스 접근 허용
        //http.authorizeRequests().antMatchers("/**");
        http.addFilter(getAuthenticationFilter());
        http.headers().frameOptions().sameOrigin();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService,env);
        //authenticationFilter.setFilterProcessesUrl("/user-service/login");
        authenticationFilter.setAuthenticationManager(authenticationManager());

        return authenticationFilter;
    }

    //인증과 관련된 메소드
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //여기서 로그인시 들어오는 비밀번호를 encrypt한다. 그래서 DB에 저장된 비밀번호랑 비교해준다.
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
