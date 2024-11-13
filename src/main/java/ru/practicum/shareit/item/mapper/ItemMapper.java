package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

//@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public class ItemMapper {
    public ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getItemId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequestId() != null ? item.getRequestId() : null
        );
    }

    public Item toItem(ItemDto itemDto, Long ownerId) {
        return new Item(
                itemDto.getItemId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                ownerId,
                itemDto.getRequestId() != null ? itemDto.getRequestId() : null
        );
    }
}
