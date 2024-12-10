package ru.practicum.shareit.item.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
//    // добавление вещи
//    Item add(Item item);
//
//    // обновление вещи
//    Item update(Item item);
//
    // получение списка вещей владельца
    List<Item> getItemsByOwner(User owner);

    // получение списка вещей по запросу пользователя
    @Query(" select i from Item i " +
            "where lower(i.name) like lower(concat('%', :search, '%')) " +
            " or lower(i.description) like lower(concat('%', :search, '%')) " +
            " and i.available = true")
    List<Item> getItemsBySearchQuery(@Param("search") String text);

    List<Item> findByNameIgnoreCaseAndAvailableTrue(String text);
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
