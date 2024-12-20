package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void createUser() {
        UserDto userDto = new UserDto();
        userDto.setName("newName");
        userDto.setEmail("a@a.com");
        UserDto user = userService.createUser(userDto);
        assertEquals("newName", user.getName());
    }

    @Test
    void removeUser() {
        User user = new User();
        user.setName("name");
        user.setEmail("b@b.com");
        User savedUser = userRepository.save(user);

        userService.removeUser(savedUser.getId());
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertTrue(foundUser.isEmpty());
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setName("name");
        user.setEmail("c@c.com");
        User savedUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setName("newName");
        userDto.setEmail("d@d.com");
        UserDto updatedUser = userService.updateUser(userDto, savedUser.getId());
        assertEquals("newName", updatedUser.getName());
    }

    @Test
    void getUsers() {
        Collection<UserDto> users = userService.getUsers();
        assertFalse(users.isEmpty());
    }

    @Test
    void getUserById() {
        User user = new User();
        user.setName("name");
        user.setEmail("e@e.com");
        User savedUser = userRepository.save(user);

        UserDto users = userService.getUserById(savedUser.getId());
        assertNotNull(users);
    }

}
