package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    // добавление вещи
    Item add(Item item);

    // обновление вещи
    Item update(Item item);

    // удаление вещи
    void delete(Long userId);

    // получение списка вещей владельца
    List<Item> getItemsByOwner(Long ownerId);

    // получение списка вещей по запросу
    List<Item> getItemsBySearchQuery(String text);

    void deleteItemsByOwner(Long ownerId);

    // получение вещи по id
    Item getItemById(Long itemId);
}
