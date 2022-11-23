package koo.MicroServiceUserService.controller;

import koo.MicroServiceUserService.dto.UserDto;
import koo.MicroServiceUserService.repository.UserEntity;
import koo.MicroServiceUserService.service.UserService;
import koo.MicroServiceUserService.vo.Greeting;
import koo.MicroServiceUserService.vo.RequestUser;
import koo.MicroServiceUserService.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Autowired
    private Environment env;

    @Autowired
    private Greeting gretting;

    @GetMapping("/health_check")
    public String status() {
//      return String.format("It's working in User Service on PORT %s", env.getProperty("local.server.port"));
        return String.format("It's working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }

    @GetMapping("/welcome")
    public String welcome() {
//        return env.getProperty("greeting.message");
        return gretting.getMessage();
    }

    @PostMapping("/users") // 사용자 회원가입
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper(); //  ModelMapper는 DTO를 엔티티로 바꿀때 쉽게 바꿀수 있게 해주는 해준다. (pom.xml에서 의존성을 추가해야한다.)
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // 딱 맞아 떨어지지 않으면 변환을 못하게 전략 설정
        UserDto userDto = mapper.map(user, UserDto.class);

        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseUser);
    }

    @GetMapping("/users") // 전체 사용자 조회
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();
        List<ResponseUser> result = new ArrayList<>();

        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/users/{userId}") // 특정 사용자 조회
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);

        ResponseUser result = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

}
