package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserRepository {
    User createUser(User user); // добавление пользователя

    void removeUser(Long userId); // удаление пользователя

    User updateUser(User user); //модификация, обновление пользователя

    Collection<User> getUsers(); // Получение всех пользователей

    User getUserById(Long userId); // Получение пользователя по id
}
