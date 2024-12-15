package ru.practicum.shareit.itemrequest.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.itemrequest.dto.ItemRequestDto;
import ru.practicum.shareit.itemrequest.model.ItemRequest;

@Component
public class ItemRequestMapper {

    public ItemRequest toItemRequest(ItemRequestDto itemRequestDto) {
        return new ItemRequest(
                itemRequestDto.getItemRequestId(),
                itemRequestDto.getDescription(),
                itemRequestDto.getRequestorId(),
                itemRequestDto.getCreated()
        );
    }

    public ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        return new ItemRequestDto(
                itemRequest.getItemRequestId(),
                itemRequest.getDescription(),
                itemRequest.getRequestorId(),
                itemRequest.getCreated()
        );
    }

}
