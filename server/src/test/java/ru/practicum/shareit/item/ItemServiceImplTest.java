package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
public class ItemServiceImplTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemServiceImpl itemService;

    @Test
    void create() {

        User user = new User();
        user.setName("name");
        user.setEmail("a@a.com");
        User savedUser = userRepository.save(user);

        Item item = new Item();
        item.setName("name");
        item.setDescription("desc");
        item.setAvailable(true);
        item.setOwner(savedUser);
        Item savedItem = itemRepository.save(item);

        Item result = itemRepository.save(savedItem);

        assertNotNull(result);
        assertEquals(item.getName(), result.getName());
    }

    @Test
    void getAll() {
        User user = new User();
        user.setName("name");
        user.setEmail("a@a.com");
        User savedUser = userRepository.save(user);

        Item item = new Item();
        item.setName("name");
        item.setDescription("desc");
        item.setAvailable(true);
        item.setOwner(savedUser);
        Item savedItem = itemRepository.save(item);

        List<Item> result = itemRepository.getItemsByOwner(savedUser);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}
