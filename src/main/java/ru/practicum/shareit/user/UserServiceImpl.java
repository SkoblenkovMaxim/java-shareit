package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.UserAlreadyExistsException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.ArrayList;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(@Qualifier("inMemoryUserRepository") UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);

//        if (user == null) {
//            throw new UserNotFoundException("User not found");
//        }
//
//        if (userRepository.getUsers().contains(user)) {
//            log.debug("Пользователь с id={} уже существует", user.getUserId());
//            throw new UserNotFoundException("Пользователь с id=" + user.getUserId() + " уже существует");
//        }
//
//        if (user.getUserName() == null || user.getUserName().isEmpty()) {
//            throw new ValidationException("Отсутствует имя пользователя");
//        }
//
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidationException("Отсутствует email пользователя");
        }
//
        if (userRepository.getUsers().stream()
                .anyMatch(user2 -> user2.getEmail().equals(user.getEmail()))) {
            log.error("email={} уже используется", user.getEmail());
            throw new UserAlreadyExistsException("email=" + user.getEmail() + " уже используется");
        }

        User user1 = userRepository.createUser(user);

        return userMapper.toUserDto(user1);
    }

    @Override
    public void removeUser(Long userId) {
        if (isValidUser(userId)) {
            userRepository.removeUser(userId);
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {

        if (userDto.getUserId() == null) {
            userDto.setUserId(userId);
        }
        return userMapper.toUserDto(userRepository.updateUser(userMapper.toUser(userDto)));

//        User user;
//
//        if (isValidUser(userId)) {
//            user = userMapper.toUser(userDto);
//        }
//
////        User user = userMapper.toUser(userDto);
//
//        User userFromDb = userRepository.getUserById(user.getUserId());
//        if (userFromDb != null) {
//            return userMapper.toUserDto(userRepository.updateUser(user));
//        } else {
//            throw new UserNotFoundException("id " + user.getUserId() + " не найден");
//        }
    }

    @Override
    public Collection<UserDto> getUsers() {
        return userRepository.getUsers().stream()
                .map(userMapper::toUserDto)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User " + userId + " is not found");
        }
        return userMapper.toUserDto(user);
    }

    //Проверка наличия пользователя в хранилище
    public boolean isValidUser(Long userId) {
        if (userRepository.getUserById(userId) != null) {
            return true;
        }
        return false;
//        return userRepository.getUserById(userId) != null;
    }

//    private String getDisplayedName(User user) {
//        return user.getUserName() != null && !user.getUserName().isBlank() ? user.getUserName() : user.getEmail();
//    }
}
