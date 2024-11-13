package ru.practicum.shareit.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
//@Component
public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

//    public UserDto toUserDto(User user) {
//        return new UserDto(
//                user.getUserId(),
//                user.getUserName(),
//                user.getEmail()
//        );
//    }
//
//    public User toUser(UserDto userDto) {
//        return new User(
//                userDto.getUserId(),
//                userDto.getUserName(),
//                userDto.getEmail()
//        );
//    }
}
