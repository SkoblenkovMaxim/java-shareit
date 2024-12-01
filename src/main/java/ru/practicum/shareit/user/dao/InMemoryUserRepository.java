//package ru.practicum.shareit.user.dao;
//
//import lombok.Getter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import ru.practicum.shareit.exception.UserAlreadyExistsException;
//import ru.practicum.shareit.exception.NotFoundException;
//import ru.practicum.shareit.user.model.User;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Getter
//@Component
//public class InMemoryUserRepository implements UserRepository {
//
//    private final Map<Long, User> allUsers = new HashMap<>();
//    private Long count = 0L; // счетчик для пользователей
//
//    public InMemoryUserRepository() {
//    }
//
//    // Создание пользователя
//    @Override
//    public User createUser(User newUser) {
//
//        newUser.setId(count++);
//
//        allUsers.put(newUser.getId(), newUser);
//        return newUser;
//    }
//
//    // Удаление пользователя
//    @Override
//    public void removeUser(Long userId) {
//            allUsers.remove(userId);
//    }
//
//    // Обновление пользователя
//    @Override
//    public User updateUser(User user) {
//
//        if (user.getName() == null) {
//            user.setName(allUsers.get(user.getId()).getName());
//        }
//        if (user.getEmail() == null) {
//            user.setEmail(allUsers.get(user.getId()).getEmail());
//        }
//
//        if (isValidUser(user)) {
//            User oldUser = allUsers.get(user.getId());
//            if (user.getEmail() != null) {
//                oldUser.setEmail(user.getEmail());
//            }
//            if (user.getName() != null) {
//                oldUser.setName(user.getName());
//            }
//
//            if (allUsers.values().stream()
//                    .filter(u -> u.getEmail().equals(user.getEmail()))
//                    .allMatch(u -> u.getId().equals(user.getId()))) {
//                if (isValidUser(user)) {
//                    allUsers.put(user.getId(), user);
//                }
//            } else {
//                throw new UserAlreadyExistsException("Пользователь с E-mail=" + user.getEmail() + " уже существует!");
//            }
//
//            return oldUser;
//        }
//        log.debug("id={} не найден", user.getId());
//        throw new NotFoundException("id " + user.getId() + " не найден");
//    }
//
//    // Получение списка всех пользователей
//    @Override
//    public Collection<User> getUsers() {
//        return allUsers.values();
//    }
//
//    // Получение пользователя по id
//    @Override
//    public User getUserById(Long userId) {
//        if (allUsers.containsKey(userId)) {
//            return allUsers.get(userId);
//        }
//        log.debug("Пользователь с id={} не найден", userId);
//        throw new NotFoundException("Пользователь с id=" + userId + " не найден");
//    }
//
//    public boolean isValidUser(User user) {
//
//        if (allUsers.containsKey(user.getId())) {
//            return true;
//        }
//        log.debug("Пользователь с id={} не найден", user.getId());
//        throw new NotFoundException("Пользователь с id=" + user.getId() + " не найден");
//    }
//}
