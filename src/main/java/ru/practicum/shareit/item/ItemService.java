package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    // добавление вещи
    ItemDto add(ItemDto item, Long ownerId);

    // обновление вещи
    ItemDto update(ItemDto item);

    // получение списка вещей владельца
    List<ItemDto> getItemsByOwner(Long ownerId);

    // получение списка вещей по запросу пользователя
    List<ItemDto> getItemsBySearchQuery(String text);

    // удаление вещи
    void delete(Long itemId);

    // удаление вещей владельца
    void deleteItemsByOwner(Long ownerId);

    // получение вещи по id
    ItemDto getItemById(Long itemId);
}