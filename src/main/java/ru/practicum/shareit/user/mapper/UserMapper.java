package ru.practicum.shareit.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public class UserMapper {
    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getUserName(),
                user.getEmail()
        );
    }

    public User toUser(UserDto userDto) {
        return new User(
                userDto.getUserId(),
                userDto.getUserName(),
                userDto.getEmail()
        );
    }
}
