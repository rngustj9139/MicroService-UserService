package koo.MicroServiceUserService.service;

import koo.MicroServiceUserService.dto.UserDto;
import koo.MicroServiceUserService.repository.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService { // UserDetails는 사용자의 정보를 담는 객체를 만드는 인터페이스, UserDetailsService는 사용자의 정보를 DB에서 불러오는 인터페이스, User는 UserDetails의 구현객체

    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<UserEntity> getUserByAll();

    UserDto getUserDetailsByEmail(String userName);
}
