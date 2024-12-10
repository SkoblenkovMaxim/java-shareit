package ru.practicum.shareit.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
//    User createUser(User user); // добавление пользователя
//
//    void removeUser(Long userId); // удаление пользователя
//
//    User updateUser(User user); //модификация, обновление пользователя
//
//    Collection<User> getUsers(); // Получение всех пользователей
//
//    User getUserById(Long userId); // Получение пользователя по id
}
