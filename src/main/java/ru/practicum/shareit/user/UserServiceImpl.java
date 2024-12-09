package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.UserAlreadyExistsException;
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
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidationException("Отсутствует email пользователя");
        }

        if (userRepository.findAll().stream()
                .anyMatch(user2 -> user2.getEmail().equals(user.getEmail()))) {
            log.error("email={} уже используется", user.getEmail());
            throw new UserAlreadyExistsException("email=" + user.getEmail() + " уже используется");
        }

        User user1 = userRepository.save(user);

        return userMapper.toUserDto(user1);
    }

    @Override
    public void removeUser(Long userId) {
//        if (isValidUser(userId)) {
            userRepository.deleteById(userId);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {

        if (userDto.getId() == null) {
            userDto.setId(userId);
        }
//        else {
//            throw new UserAlreadyExistsException("Пользователь не найден");
//        }

//        if (userDto.getId().equals(userId)) {
//            throw new UserAlreadyExistsException("Пользователь не найден");
//        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }

//        if (userRepository.findAll().stream()
//                .filter(user2 -> user.getEmail().equals(userDto.getEmail()))
//                .anyMatch(user2 -> user2.getId().equals(userDto.getId()))) {
//            user.setEmail(userDto.getEmail());
//        }
        if(userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
//        else {
//            log.error("email={} уже используется", user.getEmail());
//            throw new UserAlreadyExistsException("email=" + user.getEmail() + " уже используется");
//        }

        return userMapper.toUserDto(userRepository.save(userMapper.toUser(userDto)));
    }

    @Override
    public Collection<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserDto)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.getReferenceById(userId);
        return userMapper.toUserDto(user);
    }
}
