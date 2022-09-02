package koo.MicroServiceUserService.controller;

import koo.MicroServiceUserService.dto.UserDto;
import koo.MicroServiceUserService.service.UserService;
import koo.MicroServiceUserService.vo.Greeting;
import koo.MicroServiceUserService.vo.RequestUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

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
        return "It's working in User Service";
    }

    @GetMapping("/welcome")
    public String welcome() {
//        return env.getProperty("greeting.message");
        return gretting.getMessage();
    }

    @PostMapping("/users")
    public String createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper(); //  ModelMapper는 DTO를 엔티티로 바꿀때 쉽게 바꿀수 있게 해주는 해준다. (pom.xml에서 의존성을 추가해야한다.)
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // 딱 맞아 떨어지지 않으면 변환을 못하게 전략 설정
        UserDto userDto = mapper.map(user, UserDto.class);

        userService.createUser(userDto);

        return "Create user method is called";
    }

}
