package koo.MicroServiceUserService.service;

import koo.MicroServiceUserService.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);

}
