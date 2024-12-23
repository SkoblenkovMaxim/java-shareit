package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.practicum.shareit.booking.dto.BookUpdateRequestDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingInputDto;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookingServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;

    @Test
    void bookItem() {
        User user = new User();
        user.setName("name");
        user.setEmail("a@a.com");
        User savedUser = userRepository.save(user);

        User userBooker = new User();
        userBooker.setName("name");
        userBooker.setEmail("b@b.com");
        User savedUserBooker = userRepository.save(userBooker);

        Item item = new Item();
        item.setName("name");
        item.setDescription("desc");
        item.setAvailable(true);
        item.setOwner(savedUser);
        Item savedItem = itemRepository.save(item);

        BookingInputDto inputDto = new BookingInputDto();
        inputDto.setItemId(savedItem.getId());
        inputDto.setStart(LocalDateTime.parse("2015-08-04T10:11:30"));
        inputDto.setEnd(LocalDateTime.parse("2015-08-05T10:11:30"));

        BookingDto dto = bookingService.bookItem(inputDto, savedUserBooker.getId());
        assertNotNull(dto);
    }

    @Test
    void update() {
        User user = new User();
        user.setName("name");
        user.setEmail("a@a.com");
        User savedUser = userRepository.save(user);

        User userBooker = new User();
        userBooker.setName("name");
        userBooker.setEmail("b@b.com");
        User savedUserBooker = userRepository.save(userBooker);

        Item item = new Item();
        item.setName("name");
        item.setDescription("desc");
        item.setAvailable(true);
        item.setOwner(savedUser);
        Item savedItem = itemRepository.save(item);

        Booking booking = new Booking();
        booking.setStart(LocalDateTime.parse("2015-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2025-08-05T10:11:30"));
        booking.setItem(savedItem);
        booking.setBooker(savedUserBooker);
        booking.setStatus(Status.WAITING);

        Booking savedBooking = bookingRepository.save(booking);

        BookUpdateRequestDto dto = new BookUpdateRequestDto();
        dto.setApproved(Boolean.TRUE);

        BookingDto shortDto = bookingService
                .update(savedBooking.getId(), savedUser.getId(), dto);
        assertNotNull(shortDto);
    }

    @Test
    void getBookingById() {
        User user = new User();
        user.setName("name");
        user.setEmail("a@a.com");
        User savedUser = userRepository.save(user);

        User userBooker = new User();
        userBooker.setName("name");
        userBooker.setEmail("b@b.com");
        User savedUserBooker = userRepository.save(userBooker);

        Item item = new Item();
        item.setName("name");
        item.setDescription("desc");
        item.setAvailable(true);
        item.setOwner(savedUser);
        Item savedItem = itemRepository.save(item);

        Booking booking = new Booking();
        booking.setStart(LocalDateTime.parse("2015-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2015-08-05T10:11:30"));
        booking.setItem(savedItem);
        booking.setBooker(savedUserBooker);
        booking.setStatus(Status.WAITING);

        Booking savedBooking = bookingRepository.save(booking);

        BookingDto dto = bookingService.getBookingById(savedBooking.getId(), savedUser.getId());
        assertNotNull(dto);
    }

    @Test
    void getBookings() {
        User user = new User();
        user.setName("name");
        user.setEmail("a@a.com");
        User savedUser = userRepository.save(user);

        User userBooker = new User();
        userBooker.setName("name");
        userBooker.setEmail("b@b.com");
        User savedUserBooker = userRepository.save(userBooker);

        Item item = new Item();
        item.setName("name");
        item.setDescription("desc");
        item.setAvailable(true);
        item.setOwner(savedUser);
        Item savedItem = itemRepository.save(item);

        Booking booking = new Booking();
        booking.setStart(LocalDateTime.parse("2015-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2015-08-05T10:11:30"));
        booking.setItem(savedItem);
        booking.setBooker(savedUserBooker);
        booking.setStatus(Status.WAITING);

        bookingRepository.save(booking);

        List<BookingDto> dtoList = bookingService
                .getBookings("ALL", savedUserBooker.getId());
        assertFalse(dtoList.isEmpty());
    }

    @Test
    void getBookingsOwner() {
        User user = new User();
        user.setName("name");
        user.setEmail("a@a.com");
        User savedUser = userRepository.save(user);

        User userBooker = new User();
        userBooker.setName("name");
        userBooker.setEmail("b@b.com");
        User savedUserBooker = userRepository.save(userBooker);

        Item item = new Item();
        item.setName("name");
        item.setDescription("desc");
        item.setAvailable(true);
        item.setOwner(savedUser);
        Item savedItem = itemRepository.save(item);

        Booking booking = new Booking();
        booking.setStart(LocalDateTime.parse("2015-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2015-08-05T10:11:30"));
        booking.setItem(savedItem);
        booking.setBooker(savedUserBooker);
        booking.setStatus(Status.WAITING);

        bookingRepository.save(booking);

        List<BookingDto> dtoList = bookingService
                .getBookingsOwner("ALL", savedUser.getId());
        assertFalse(dtoList.isEmpty());
    }

    @Test
    void getLastBooking() {
        User user = new User();
        user.setName("name");
        user.setEmail("a@a.com");
        User savedUser = userRepository.save(user);

        User userBooker = new User();
        userBooker.setName("name");
        userBooker.setEmail("b@b.com");
        User savedUserBooker = userRepository.save(userBooker);

        Item item = new Item();
        item.setName("name");
        item.setDescription("desc");
        item.setAvailable(true);
        item.setOwner(savedUser);
        Item savedItem = itemRepository.save(item);

        Booking booking = new Booking();
        booking.setStart(LocalDateTime.parse("2015-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2015-08-05T10:11:30"));
        booking.setItem(savedItem);
        booking.setBooker(savedUserBooker);
        booking.setStatus(Status.WAITING);

        bookingRepository.save(booking);

        BookingShortDto dto = bookingService.getLastBooking(savedItem.getId());
        assertNotNull(dto);
    }

    @Test
    void getNextBooking() {
        User user = new User();
        user.setName("name");
        user.setEmail("a@a.com");
        User savedUser = userRepository.save(user);

        User userBooker = new User();
        userBooker.setName("name");
        userBooker.setEmail("b@b.com");
        User savedUserBooker = userRepository.save(userBooker);

        Item item = new Item();
        item.setName("name");
        item.setDescription("desc");
        item.setAvailable(true);
        item.setOwner(savedUser);
        Item savedItem = itemRepository.save(item);

        Booking booking = new Booking();
        booking.setStart(LocalDateTime.parse("2025-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2025-08-05T10:11:30"));
        booking.setItem(savedItem);
        booking.setBooker(savedUserBooker);
        booking.setStatus(Status.WAITING);

        bookingRepository.save(booking);

        BookingShortDto dto = bookingService.getNextBooking(savedItem.getId());
        assertNotNull(dto);
    }

    @Test
    void getBookingWithUserBookedItem() {
        User user = new User();
        user.setName("name");
        user.setEmail("a@a.com");
        User savedUser = userRepository.save(user);

        User userBooker = new User();
        userBooker.setName("name");
        userBooker.setEmail("b@b.com");
        User savedUserBooker = userRepository.save(userBooker);

        Item item = new Item();
        item.setName("name");
        item.setDescription("desc");
        item.setAvailable(true);
        item.setOwner(savedUser);
        Item savedItem = itemRepository.save(item);

        Booking bookingToSave = new Booking();
        bookingToSave.setStart(LocalDateTime.parse("2015-08-04T10:11:30"));
        bookingToSave.setEnd(LocalDateTime.parse("2015-08-05T10:11:30"));
        bookingToSave.setItem(savedItem);
        bookingToSave.setBooker(savedUserBooker);
        bookingToSave.setStatus(Status.APPROVED);

        bookingRepository.save(bookingToSave);

        Booking booking = bookingService
                .getBookingWithUserBookedItem(savedItem.getId(), savedUserBooker.getId());
        assertNotNull(booking);
    }

}
