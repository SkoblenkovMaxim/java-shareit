package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dao.CommentRepository;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.itemrequest.RequestRepository;
import ru.practicum.shareit.itemrequest.model.ItemRequest;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RequestRepository itemRequestRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    private ItemMapper itemMapper;
    private UserMapper userMapper;
    private User user;
    private User owner;
    private UserDto userDto;
    private Item item;
    private ItemDto itemDto;
    private Comment comment;
    private CommentDto commentDto;
    private ItemRequest itemRequest;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Test User")
                .email("testuser@example.com")
                .build();

        userDto = userMapper.toUserDto(user);

        owner = User.builder()
                .id(2L)
                .name("Owner")
                .email("owner@example.com")
                .build();

        itemRequest = ItemRequest.builder()
                .itemRequestId(1L)
                .requestorId(user.getId())
                .description("Request Description")
                .created(LocalDateTime.now())
                .build();

        item = Item.builder()
                .id(1L)
                .name("Test Item")
                .description("Test Description")
                .available(true)
                .owner(owner)
                .requestId(itemRequest.getItemRequestId())
                .build();

        itemDto = itemMapper.toItemDto(item);

        comment = Comment.builder()
                .id(1L)
                .text("Great item!")
                .item(item)
                .author(user)
                .created(LocalDateTime.now())
                .build();

        commentDto = new CommentDto(
                1L,
                "Comment Text",
                owner.getName().toString(),
                itemDto,
                userDto,
                LocalDateTime.now()
        );
    }

    @Test
    void create() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRequestRepository.findById(anyLong())).thenReturn(Optional.of(itemRequest));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDto result = itemService.add(itemDto, user.getId());

        assertNotNull(result);
        assertEquals(itemDto.getName(), result.getName());
        verify(userRepository, times(1)).findById(user.getId());
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void createWithInvalidUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            itemService.add(itemDto, 999L);
        });

        assertEquals("Пользователь с id=999L не найден", exception.getMessage());
        verify(userRepository, times(1)).findById(999L);
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void update() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(owner));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDto result = itemService.update(itemDto, owner.getId(), item.getId());

        assertNotNull(result);
        assertEquals(item.getName(), result.getName());
        verify(userRepository, times(1)).findById(owner.getId());
        verify(itemRepository, times(1)).findById(item.getId());
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void updateWithInvalidUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            itemService.update(itemDto, 999L, itemDto.getId());
        });

        assertEquals("Пользователь с id=999L не найден", exception.getMessage());
        verify(userRepository, times(1)).findById(999L);
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void updateWithInvalidItem() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            itemService.update(itemDto, user.getId(), 999L);
        });

        assertEquals("Вещь с id = 999L не найдена", exception.getMessage());
        verify(itemRepository, times(1)).findById(999L);
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void getItemById() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        ItemDto result = itemService.getItemById(item.getId());

        assertNotNull(result);
        assertEquals(itemDto.getName(), result.getName());
        verify(itemRepository, times(1)).findById(item.getId());
    }

    @Test
    void searchItem() {
        when(itemRepository.findByNameIgnoreCaseAndAvailableTrue(anyString()))
                .thenReturn(Collections.singletonList(item));

        List<ItemDto> result = itemService.getItemsBySearchQuery("Test");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(itemRepository, times(1)).findByNameIgnoreCaseAndAvailableTrue(anyString());
    }

    @Test
    void searchItemWithEmptyText() {
        List<ItemDto> result = itemService.getItemsBySearchQuery("");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(itemRepository, never()).findByNameIgnoreCaseAndAvailableTrue(anyString());
    }

    @Test
    void getAll() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(owner));
        when(itemRepository.getItemsByOwner(any())).thenReturn(Collections.singletonList(item));

        List<ItemDto> result = itemService.getItemsByOwner(owner.getId());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(itemRepository, times(1)).getItemsByOwner(owner);
    }

    @Test
    void addComment() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(commentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        CommentDto result = itemService.addComment(commentDto, 1L, 1L);
        assertNotNull(result);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void createShouldThrowNotFoundException_WhenUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemService.add(itemDto, user.getId()));
    }

    @Test
    void updateShouldReturnUpdatedItem() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(owner));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDto result = itemService.update(itemDto, owner.getId(), item.getId());

        assertNotNull(result);
        assertEquals(item.getId(), result.getId());
        verify(itemRepository, times(1)).save(any(Item.class));
    }
}
