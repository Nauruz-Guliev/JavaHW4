package ru.kpfu.itis.gnt.registration.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.gnt.registration.dto.UserDto;
import ru.kpfu.itis.gnt.registration.entity.UserEntity;
import ru.kpfu.itis.gnt.registration.services.UserService;

@Mapper(componentModel = "spring", uses = UserService.class)
public interface UserMapper {

    UserEntity userDtoToUserEntity(UserDto dto);
    UserDto userEntityToUserDto(UserEntity entity);
}
