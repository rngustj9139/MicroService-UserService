package koo.MicroServiceUserService.security;


import koo.MicroServiceUserService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;

    @Override
    protected void configure(HttpSecurity http) throws Exception { // 인가에 관한 configure 함수
        http.csrf().disable();
//      http.authorizeRequests().antMatchers("/users/**").permitAll();
        http.authorizeRequests()
                .antMatchers("/error/**").permitAll()
                .antMatchers("/**").permitAll()
//                .hasIpAddress("127.0.0.1") // 이 IP를 가진사람만 인가
                .and()
                .addFilter(getAuthenticationFilter()); // 인증 필터를 통과했을 경우에만 인가한다.

        http.headers().frameOptions().disable(); // 이래야지 h2-console에 접속했을떼 오류가 발생하지 않는다.
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager());

        return authenticationFilter;
    }

    // select pwd from users where email = ? (해당 이메일을 갖는 유저가 없다면 없다고 반환해야함)
    // db_pwd(encrypted) == input_pwd(encrypted) 체크
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // 인증에 관한 configure 함수
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder); // 위의 첫번째 주석을 처리한다. (유저가 존재한다면 사용자가 입력한 패스워드를 암호화한다.)
    }

}
