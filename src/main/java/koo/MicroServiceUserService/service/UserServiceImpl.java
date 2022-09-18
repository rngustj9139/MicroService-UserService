package koo.MicroServiceUserService.service;

import koo.MicroServiceUserService.dto.UserDto;
import koo.MicroServiceUserService.repository.UserEntity;
import koo.MicroServiceUserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper(); //  ModelMapper는 DTO를 엔티티로 바꿀때 쉽게 바꿀수 있게 해주는 해준다. (pom.xml에서 의존성을 추가해야한다.)
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // 딱 맞아 떨어지지 않으면 변환을 못하게 전략 설정
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd("encrypted_password");

        userRepository.save(userEntity);

        UserDto returnUserDto = mapper.map(userEntity, UserDto.class);

        return returnUserDto;
    }

}
