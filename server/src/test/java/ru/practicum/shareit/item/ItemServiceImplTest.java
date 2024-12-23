package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
public class ItemServiceImplTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private ItemMapper itemMapper;

    @Test
    void deleteItem() {
        User user = new User();
        user.setName("name");
        user.setEmail("ab@ab.com");
        User savedUser = userRepository.save(user);

        Item item = new Item();
        item.setName("name");
        item.setDescription("desc");
        item.setAvailable(true);
        item.setOwner(savedUser);
        Item savedItem = itemRepository.save(item);

        itemService.deleteItem(savedItem.getId(), savedUser.getId());

        Assertions.assertEquals(Optional.empty(), itemRepository.findById(item.getId()));
    }

    @Test
    void create() {
        User user = new User();
        user.setName("name");
        user.setEmail("abc@abc.com");
        User savedUser = userRepository.save(user);

        Item item = new Item();
        item.setName("name");
        item.setDescription("desc");
        item.setAvailable(true);
        item.setOwner(savedUser);
        Item savedItem = itemRepository.save(item);

        ItemDto itemDto = itemMapper.toItemDto(savedItem);

        itemService.add(itemDto, savedUser.getId());

        Assertions.assertEquals(1, itemRepository.count());
    }

    @Test
    void update() {
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

        ItemDto itemDto = new ItemDto();
        itemDto.setName("newName");
        itemDto.setDescription("newDesc");
        itemDto.setAvailable(false);

        itemService.update(
                itemMapper.toItemDto(item),
                savedUser.getId(),
                savedItem.getId()
        );

        Assertions.assertEquals("newName", itemDto.getName());
        Assertions.assertEquals("newDesc", itemDto.getDescription());
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
