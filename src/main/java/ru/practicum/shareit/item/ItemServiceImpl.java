package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private ItemMapper itemMapper;

    @Autowired
    public ItemServiceImpl(@Qualifier("inMemoryItemRepository") ItemRepository itemRepository,
                           @Qualifier("inMemoryUserRepository") UserRepository userRepository,
                           ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public ItemDto add(ItemDto itemDto, Long id) {
        Item item = itemMapper.toItem(itemDto, id);

        if (id == null) {
            throw new NotFoundException("Пользователь не найден");
        }

        if (userRepository.getUserById(id) == null) {
            throw new NotFoundException("Пользователь с id=" + id + " не найден");
        }

        Item itemNew = itemRepository.add(item);
        return itemMapper.toItemDto(itemNew);
    }

    @Override
    public ItemDto update(ItemDto itemDto, Long ownerId, Long itemId) {

        if (itemRepository.getItemById(itemId) == null) {
            itemDto.setId(itemId);
        }

        Item oldItem = itemRepository.getItemById(itemId);

        if (!oldItem.getOwnerId().equals(ownerId)) {
            throw new NotFoundException("Такая вещь у пользователя не найдена");
        }

        return itemMapper.toItemDto(itemRepository.update(itemMapper.toItem(itemDto, itemId)));
    }

    @Override
    public List<ItemDto> getItemsByOwner(Long ownerId) {
        return itemRepository.getItemsByOwner(ownerId).stream().map(itemMapper::toItemDto).collect(toList());
    }

    @Override
    public List<ItemDto> getItemsBySearchQuery(String text) {
        text = text.toUpperCase();
            return itemRepository.getItemsBySearchQuery(text)
                    .stream()
                    .map(itemMapper::toItemDto)
                    .collect(toList());
    }

    @Override
    public void deleteItem(Long itemId, Long ownerId) {
        itemRepository.delete(itemId);
    }

    @Override
    public void deleteItemsByOwner(Long ownerId) {
        itemRepository.deleteItemsByOwner(ownerId);
    }

    @Override
    public ItemDto getItemById(Long itemId) {
        return itemMapper.toItemDto(itemRepository.getItemById(itemId));
    }
}
