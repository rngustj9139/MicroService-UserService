package koo.MicroServiceUserService.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import koo.MicroServiceUserService.vo.RequestLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter { // 로그인 시도시 제일 먼저 작동되는 필터

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // ObjectMapper는 https://velog.io/@zooneon/Java-ObjectMapper%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%98%EC%97%AC-JSON-%ED%8C%8C%EC%8B%B1%ED%95%98%EA%B8%B0 참고
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class); // 인풋스트림에 값이 담겨서 전달되면 그걸 우리가 원하는 클래스로 변경함

            // 로그인 시도
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>())); // ArrayList는 어떤 권한들을 갖는지 알게해주는 인자이다.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info(((User) authResult.getPrincipal()).getUsername()); // User는 UserServiceImpl의 loadUserByUsername에서 반환되는 객체이다.(로그인 성공시 반환되는 객체)

        // 이후 로그인이 유지될 수 있도록 JWT 발행 (json Web Token)
    }

}
