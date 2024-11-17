package ru.practicum.shareit.item.dao;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Component
public class InMemoryItemRepository implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private Long count = 0L;

    public InMemoryItemRepository() {
    }

    // добавление новой вещи
    @Override
    public Item add(Item item) {
        item.setId(count++);
        items.put(item.getId(), item);
        return item;
    }

    // редактирование вещи
    @Override
    public Item update(Item item) {

        items.put(item.getId(), item);
        return item;
    }

    // получение списка вещей владельца
    @Override
    public List<Item> getItemsByOwner(Long ownerId) {
        return items.values().stream()
                .filter(item -> item.getOwnerId().equals(ownerId))
                .toList();
    }

    // получение списка вещей по запросу пользователя
    @Override
    public List<Item> getItemsBySearchQuery(String text) {
        List<Item> resultSearch = new ArrayList<>();
        if (!text.isBlank()) {
            for (Item item : items.values()) {
                if (item.getAvailable() != null && item.getAvailable()) {
                    if (item.getName().toUpperCase().contains(text.toUpperCase())
                            || item.getDescription().toUpperCase().contains(text.toUpperCase())) {
                        resultSearch.add(item);
                    }
                }
            }
        }

        return resultSearch;
    }

    // удаление вещи
    public void delete(Long itemId) {
        items.remove(itemId);
    }

    // удаление вещей владельца
    @Override
    public void deleteItemsByOwner(Long ownerId) {
        List<Long> deleteItems = new ArrayList<>(items.values().stream()
                .filter(item -> item.getOwnerId().equals(ownerId))
                .map(Item::getId)
                .toList());
        for (Long deleteItem : deleteItems) {
            items.remove(deleteItem);
        }
    }

    // просмотр информации о вещи по id
    @Override
    public Item getItemById(Long itemId) {
        return items.get(itemId);
    }
}
