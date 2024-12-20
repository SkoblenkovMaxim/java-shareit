package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingInputDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingServiceTest {

    private final BookingService bookingService;
    private final UserService userService;
    private final ItemService itemService;
    private final User user = new User(300L, "First", "first@first300.ru");
    private final UserDto userDto1 = new UserDto(301L, "AlexOne", "alexone@alex300.ru");
    private final UserDto userDto2 = new UserDto(302L, "AlexTwo", "alextwo@alex300.ru");
    private final ItemDto itemDto1 = new ItemDto(301L, "Item1", "Description1", true,
            userDto1, null, null, null, null);
    private final ItemDto itemDto2 = new ItemDto(302L, "Item2", "Description2", true,
            userDto2, null, null, null, null);

    @Test
    void shouldExceptionWhenCreateBookingByOwnerItem() {
        UserDto ownerDto = userService.createUser(userDto1);
        ItemDto newItemDto = itemService.add(itemDto1, ownerDto.getId());
        BookingInputDto bookingInputDto = new BookingInputDto(
                newItemDto.getId(),
                LocalDateTime.of(2030, 12, 25, 12, 00, 00),
                LocalDateTime.of(2030, 12, 26, 12, 00, 00));
        NotFoundException exp = assertThrows(NotFoundException.class,
                () -> bookingService.bookItem(bookingInputDto, ownerDto.getId()));
        assertEquals("Вещь с ID=" + newItemDto.getId() + " недоступна для бронирования самим владельцем!",
                exp.getMessage());
    }

    @Test
    void shouldExceptionWhenGetBookingByNotOwnerOrNotBooker() {
        UserDto ownerDto = userService.createUser(userDto1);
        UserDto newUserDto = userService.createUser(userDto2);
        UserDto userDto3 = new UserDto(303L, "AlexThird", "alexthird@alex300.ru");
        userDto3 = userService.createUser(userDto3);
        Long userId = userDto3.getId();
        ItemDto newItemDto = itemService.add(itemDto1, ownerDto.getId());
        BookingInputDto bookingInputDto = new BookingInputDto(
                newItemDto.getId(),
                LocalDateTime.of(2030, 12, 25, 12, 00, 00),
                LocalDateTime.of(2030, 12, 26, 12, 00, 00));
        BookingDto bookingDto = bookingService.bookItem(bookingInputDto, newUserDto.getId());
        NotFoundException exp = assertThrows(NotFoundException.class,
                () -> bookingService.getBookingById(bookingDto.getId(), userId));
        assertEquals("Посмотреть данные бронирования может только владелец вещи или бронирующий ее!",
                exp.getMessage());
    }

    @Test
    void shouldReturnBookingsWhenGetBookingsByBookerAndSizeIsNull() {
        UserDto ownerDto = userService.createUser(userDto1);
        UserDto newUserDto = userService.createUser(userDto2);
        ItemDto newItemDto = itemService.add(itemDto1, ownerDto.getId());
        BookingInputDto bookingInputDto = new BookingInputDto(
                newItemDto.getId(),
                LocalDateTime.of(2030, 12, 25, 12, 00, 00),
                LocalDateTime.of(2030, 12, 26, 12, 00, 00));
        bookingService.bookItem(bookingInputDto, newUserDto.getId());
        BookingInputDto bookingInputDto1 = new BookingInputDto(
                newItemDto.getId(),
                LocalDateTime.of(2031, 12, 25, 12, 00, 00),
                LocalDateTime.of(2031, 12, 26, 12, 00, 00));
        bookingService.bookItem(bookingInputDto1, newUserDto.getId());
        List<BookingDto> listBookings = bookingService.getBookings("ALL", newUserDto.getId());
        assertEquals(2, listBookings.size());
    }

    @Test
    void shouldReturnBookingsWhenGetBookingsByBookerAndSizeIsNotNull() {
        UserDto ownerDto = userService.createUser(userDto1);
        UserDto newUserDto = userService.createUser(userDto2);
        ItemDto newItemDto = itemService.add(itemDto1, ownerDto.getId());
        BookingInputDto bookingInputDto = new BookingInputDto(
                newItemDto.getId(),
                LocalDateTime.of(2030, 12, 25, 12, 00, 00),
                LocalDateTime.of(2030, 12, 26, 12, 00, 00));
        bookingService.bookItem(bookingInputDto, newUserDto.getId());
        BookingInputDto bookingInputDto1 = new BookingInputDto(
                newItemDto.getId(),
                LocalDateTime.of(2031, 12, 25, 12, 00, 00),
                LocalDateTime.of(2031, 12, 26, 12, 00, 00));
        bookingService.bookItem(bookingInputDto1, newUserDto.getId());
        List<BookingDto> listBookings = bookingService.getBookings("ALL", newUserDto.getId());
        assertEquals(1, listBookings.size());
    }

    @Test
    void shouldReturnBookingsWhenGetBookingsInWaitingStatusByBookerAndSizeIsNull() {
        UserDto ownerDto = userService.createUser(userDto1);
        UserDto newUserDto = userService.createUser(userDto2);
        ItemDto newItemDto = itemService.add(itemDto1, ownerDto.getId());
        BookingInputDto bookingInputDto = new BookingInputDto(
                newItemDto.getId(),
                LocalDateTime.of(2030, 12, 25, 12, 00, 00),
                LocalDateTime.of(2030, 12, 26, 12, 00, 00));
        bookingService.bookItem(bookingInputDto, newUserDto.getId());
        BookingInputDto bookingInputDto1 = new BookingInputDto(
                newItemDto.getId(),
                LocalDateTime.of(2031, 12, 25, 12, 00, 00),
                LocalDateTime.of(2031, 12, 26, 12, 00, 00));
        bookingService.bookItem(bookingInputDto1, newUserDto.getId());
        List<BookingDto> listBookings = bookingService.getBookings("WAITING", newUserDto.getId());
        assertEquals(2, listBookings.size());
    }

}
