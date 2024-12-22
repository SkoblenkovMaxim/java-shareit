package ru.practicum.shareit.itemrequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.itemrequest.dto.ItemRequestDto;
import ru.practicum.shareit.itemrequest.mapper.ItemRequestMapper;
import ru.practicum.shareit.itemrequest.model.ItemRequest;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ItemRequestServiceTest {
    @Autowired
    private RequestService requestService;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRequestMapper itemRequestMapper;
    @Autowired
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        requestRepository = mock(RequestRepository.class);
        requestService = new RequestServiceImpl(
                requestRepository,
                itemRequestMapper,
                itemService
        );
    }

    @Test
    void getUserRequests() {
        User user = User.builder()
                .id(1L)
                .name("Name")
                .email("abc@abc.com")
                .build();

        when(userRepository.save(any())).thenReturn(user);

        ItemRequest itemRequest = ItemRequest.builder()
                .itemRequestId(1L)
                .description("Text")
                .requestorId(user.getId())
                .created(LocalDateTime.now())
                .build();

        ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(itemRequest, null);
        List<ItemRequestDto> result = requestService.getUserRequests(user.getId());
        Assertions.assertNotNull(result);
    }
}
