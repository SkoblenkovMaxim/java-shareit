package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

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
    void getLastBooking() {
        User user = new User();
        user.setName("name");
        user.setEmail("a@a.com");
        User savedUser = userRepository.save(user);

        User userBooker = new User();
        userBooker.setName("name");
        userBooker.setEmail("b@b.com");
        User savedUserBooker = userRepository.save(user);

        Item item = new Item();
        item.setName("name");
        item.setDescription("desc");
        item.setAvailable(true);
        item.setOwner(savedUser);
        Item savedItem = itemRepository.save(item);

        Booking booking = new Booking();
        booking.setStart(LocalDateTime.parse("2015-08-04T10:11:30"));
        booking.setEnd(LocalDateTime.parse("2015-08-04T10:11:30"));
        booking.setItem(savedItem);
        booking.setBooker(savedUserBooker);
        booking.setStatus(Status.WAITING);

        Booking savedBooking = bookingRepository.save(booking);

        BookingShortDto dto = bookingService.getLastBooking(savedBooking.getId());
        assertNotNull(dto);
    }
}
