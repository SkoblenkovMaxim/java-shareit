package ru.practicum.shareit.item.dao;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryItemRepository implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();

    // счетчик ид вещи
    protected Long getNextId() {
        return (long) items.size() + 1;
    }

    // добавление новой вещи
    @Override
    public Item add(Item itemDto) {
        itemDto.setItemId(getNextId());
        items.put(itemDto.getItemId(), itemDto);
        return itemDto;
    }

    // редактирование вещи
    @Override
    public Item update(Item item) {
        items.put(item.getItemId(), item);
        return item;
    }

    // просмотр информации о вещи
    @Override
    public Item getItemById(Long itemId) {
        return items.get(itemId);
    }

    // получение списка вещей владельца
    @Override
    public List<Item> getItemsByOwner(Long ownerId) {
        List<Item> itemsOwner = items.values().stream()
                .filter(item -> item.getOwnerId().equals(ownerId))
                .toList();
        return itemsOwner;
    }

    // удаление вещи
    public void delete(Long itemId) {
        items.remove(itemId);
    }
}
