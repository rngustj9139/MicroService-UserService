package koo.MicroServiceUserService.service;

import koo.MicroServiceUserService.dto.UserDto;
import koo.MicroServiceUserService.repository.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<UserEntity> getUserByAll();

}
