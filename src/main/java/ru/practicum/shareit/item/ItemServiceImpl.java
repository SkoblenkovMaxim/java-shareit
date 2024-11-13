package ru.practicum.shareit.item;

import org.mapstruct.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private ItemMapper itemMapper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository,
                           ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public ItemDto add(ItemDto itemDto, Long ownerId) {
        Item item = itemMapper.toItem(itemDto, ownerId);
        if (item == null) {
            throw new ItemNotFoundException("Вещь с itemId: " + itemDto.getItemId() + " не найдена");
        }

        isValidItem(item);

        if (ownerId == null) {
            throw new UserNotFoundException("Пользователь с ownerId: " + ownerId + " не найден");
        }

        Item itemNew = itemRepository.add(item);
        return itemMapper.toItemDto(itemNew);
    }

    @Override
    public ItemDto update(ItemDto item) {
        return null;
    }

    @Override
    public List<ItemDto> getItemsByOwner(Long ownerId) {
        return List.of();
    }

    @Override
    public List<ItemDto> getItemsBySearchQuery(String text) {
        return List.of();
    }

    @Override
    public void delete(Long itemId) {

    }

    @Override
    public void deleteItemsByOwner(Long ownerId) {

    }

    @Override
    public ItemDto getItemById(Long itemId) {
        return null;
    }

    // проверка наличия вещи
    public Boolean isValidItem(Item item) {
        if (item.getName() == null
                || item.getName().isEmpty()
                || item.getDescription() == null
                || item.getDescription().isEmpty()
                || item.getAvailable() == null) {
            throw new ValidationException("Название, описание и/или статус не должны быть пустыми");
        }
        return true;
    }
}
