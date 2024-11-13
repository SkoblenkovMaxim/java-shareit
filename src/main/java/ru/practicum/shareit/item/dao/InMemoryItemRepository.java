package ru.practicum.shareit.item.dao;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryItemRepository implements ItemRepository {
    private Map<Long, Item> items;
    private Long count;

    public InMemoryItemRepository() {
        items = new HashMap<>();
        count = 0L;
    }
    // добавление новой вещи
    @Override
    public Item add(Item itemDto) {
        itemDto.setItemId(count++);
        items.put(itemDto.getItemId(), itemDto);
        return itemDto;
    }

    // редактирование вещи
    @Override
    public Item update(Item item) {
        items.put(item.getItemId(), item);
        return item;
    }

    // получение списка вещей владельца
    @Override
    public List<Item> getItemsByOwner(Long ownerId) {
        List<Item> itemsOwner = items.values().stream()
                .filter(item -> item.getOwnerId().equals(ownerId))
                .toList();
        return itemsOwner;
    }

    // получение списка вещей по запросу пользователя
    @Override
    public List<Item> getItemsBySearchQuery(String text) {
        return null;
    }

    // удаление вещи
    public void delete(Long itemId) {
        items.remove(itemId);
    }

    // удаление вещей владельца
    @Override
    public void deleteItemsByOwner(Long ownerId) {

    }

    // просмотр информации о вещи по id
    @Override
    public Item getItemById(Long itemId) {
        return items.get(itemId);
    }
}
