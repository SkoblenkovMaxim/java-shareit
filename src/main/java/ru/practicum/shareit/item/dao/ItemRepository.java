package ru.practicum.shareit.item.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
//    // добавление вещи
//    Item add(Item item);
//
//    // обновление вещи
//    Item update(Item item);
//
//    // получение списка вещей владельца
//    List<Item> getItemsByOwner(Long ownerId);
//
//    // получение списка вещей по запросу пользователя
//    List<Item> getItemsBySearchQuery(String text);
//
//    // удаление вещи
//    void delete(Long itemId);
//
//    // удаление вещей владельца
//    void deleteItemsByOwner(Long ownerId);
//
//    // получение вещи по id
//    Item getItemById(Long itemId);
}
