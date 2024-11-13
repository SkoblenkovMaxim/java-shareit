package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {
    private ItemService itemService;
    //private ItemDto itemDto;
    private static final String owner = "X-Sharer-User-Id";

    @PostMapping
    public ItemDto addItem(@Valid @RequestBody ItemDto itemDto, @RequestHeader(owner) Long ownerId) {
        log.info("Получен запрос на добавление вещи пользователем с ownerId: {}", ownerId);
//        if (isExistUser(ownerId)) {
//            return itemService.add(itemDto, ownerId);
//        }
        //Item item =
        if (ownerId == null) {
            throw new ItemNotFoundException("Пользователь с ownerId=" + ownerId + " не найден");
        }


    return null;
    }

//    public boolean isExistUser(Long userId) {
//        boolean isExistUser = false;
//        if (userId != null) {
//            isExistUser = true;
//        }
//        return isExistUser;
//    }
}
