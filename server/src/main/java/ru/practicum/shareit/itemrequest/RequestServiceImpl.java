package ru.practicum.shareit.itemrequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.itemrequest.dto.ItemRequestDto;
import ru.practicum.shareit.itemrequest.mapper.ItemRequestMapper;
import ru.practicum.shareit.itemrequest.model.ItemRequest;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final ItemRequestMapper itemRequestMapper;

    @Override
    public ItemRequestDto createItemRequest(ItemRequestDto itemRequestDto) {
        ItemRequest itemRequest = itemRequestMapper.toItemRequest(itemRequestDto);
        ItemRequest savedItemRequest = requestRepository.save(itemRequest);
        return itemRequestMapper.toItemRequestDto(savedItemRequest);
    }

    @Override
    public List<ItemRequestDto> getUserRequests(Long userId) {
        return requestRepository
                .findByRequestorId(userId)
                .stream()
                .map(itemRequestMapper::toItemRequestDto)
                .collect(toList());
    }

    @Override
    public ItemRequestDto findByItemRequestId(Long itemRequestId) {
        ItemRequest itemRequest = requestRepository
                .findById(itemRequestId)
                .orElseThrow(() -> new NotFoundException("Item request с id = " + itemRequestId + " не найден."));
        return itemRequestMapper.toItemRequestDto(itemRequest);
    }

}
