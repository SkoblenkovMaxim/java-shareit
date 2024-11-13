package ru.practicum.shareit.user.dao;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.UserAlreadyExistsException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, User> allUsers = new HashMap<>();
    private Long count = 0L; // счетчик для пользователей

    public InMemoryUserRepository() {
//        allUsers = new HashMap<>();
//        count = 0L;
    }

    // Создание пользователя
    @Override
    public User createUser(User newUser) {

        newUser.setUserId(count++);

        allUsers.put(newUser.getUserId(), newUser);
        return newUser;
    }

    // Удаление пользователя
    @Override
    public void removeUser(Long userId) {
        if (isValidUser(userId)) {
            allUsers.remove(userId, allUsers.get(userId));
        } else {
            log.debug("Пользователь {} не найден", allUsers.get(userId).getUserName());
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    // Обновление пользователя
    @Override
    public User updateUser(User user) {
        if (user.getUserName().isEmpty()) {
            log.error("Имя не может быть null");
            throw new ValidationException("Имя не может быть null");
        }
        if (isValidUser(user.getUserId())) {
            User oldUser = allUsers.get(user.getUserId());
            if (user.getEmail() != null) {
                oldUser.setEmail(user.getEmail());
            }
            if (user.getUserName() != null) {
                oldUser.setUserName(user.getUserName());
            }
            return oldUser;
        }
        log.debug("id={} не найден", user.getUserId());
        throw new UserNotFoundException("id " + user.getUserId() + " не найден");
    }

    // Получение списка всех пользователей
    @Override
    public Collection<User> getUsers() {
        return allUsers.values();
    }

    // Получение пользователя по id
    @Override
    public User getUserById(Long userId) {
        if (isValidUser(userId)) {
            return allUsers.get(userId);
        }
        log.debug("Пользователь с id={} не найден", userId);
        throw new UserNotFoundException("Пользователь с id=" + userId + " не найден");
    }

    public boolean isValidUser(Long userId) {
        if (allUsers.containsKey(userId)) {
            return true;
        }
        log.debug("Пользователь с id={} не найден", userId);
        throw new UserNotFoundException("Пользователь с id=" + userId + " не найден");
    }
}
