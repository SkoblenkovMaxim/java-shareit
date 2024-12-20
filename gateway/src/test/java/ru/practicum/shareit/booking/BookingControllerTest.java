package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookUpdateRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.item.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingClient bookingClient;

    @Autowired
    private ObjectMapper mapper;

    private static final String USER_ID = "X-Sharer-User-Id";

    @Test
    void getBookings() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Name");
        userDto.setEmail("email@email.com");

        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("Name");
        itemDto.setDescription("Description");
        itemDto.setAvailable(true);
        itemDto.setOwner(userDto);
        itemDto.setLastBooking(null);
        itemDto.setNextBooking(null);
        itemDto.setComments(null);

        BookItemRequestDto requestDto = new BookItemRequestDto();
        requestDto.setItemId(itemDto.getId());
        requestDto.setStart(LocalDateTime.now());
        requestDto.setEnd(LocalDateTime.now().plusDays(1));

        when(bookingClient.getBookings(userDto.getId(), BookingState.ALL, 10, 10)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        mockMvc.perform(post("/bookings")
                .header(USER_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void bookItem() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Name");
        userDto.setEmail("email@email.com");

        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("Name");
        itemDto.setDescription("Description");
        itemDto.setAvailable(true);
        itemDto.setOwner(userDto);
        itemDto.setLastBooking(null);
        itemDto.setNextBooking(null);
        itemDto.setComments(null);

        BookItemRequestDto requestDto = new BookItemRequestDto();
        requestDto.setItemId(itemDto.getId());
        requestDto.setStart(LocalDateTime.now());
        requestDto.setEnd(LocalDateTime.now().plusDays(1));

        when(bookingClient.bookItem(userDto.getId(), requestDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        mockMvc.perform(post("/bookings")
                .header(USER_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getBooking() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Name");
        userDto.setEmail("email@email.com");

        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("Name");
        itemDto.setDescription("Description");
        itemDto.setAvailable(true);
        itemDto.setOwner(userDto);
        itemDto.setLastBooking(null);
        itemDto.setNextBooking(null);
        itemDto.setComments(null);

        BookItemRequestDto requestDto = new BookItemRequestDto();
        requestDto.setItemId(itemDto.getId());
        requestDto.setStart(LocalDateTime.now());
        requestDto.setEnd(LocalDateTime.now().plusDays(1));

        when(bookingClient.getBooking(userDto.getId(), requestDto.getItemId())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        mockMvc.perform(get("/bookings/1")
                .header(USER_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void update() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Name");
        userDto.setEmail("email@email.com");

        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("Name");
        itemDto.setDescription("Description");
        itemDto.setAvailable(true);
        itemDto.setOwner(userDto);
        itemDto.setLastBooking(null);
        itemDto.setNextBooking(null);
        itemDto.setComments(null);

        BookItemRequestDto requestDto = new BookItemRequestDto();
        requestDto.setItemId(itemDto.getId());
        requestDto.setStart(LocalDateTime.now());
        requestDto.setEnd(LocalDateTime.now().plusDays(1));

        BookUpdateRequestDto updateRequestDto = new BookUpdateRequestDto();
        updateRequestDto.setApproved(true);

        when(bookingClient.update(requestDto.getItemId(), userDto.getId(), updateRequestDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        mockMvc.perform(post("/bookings/1")
                .header(USER_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getBookingsOwner() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Name");
        userDto.setEmail("email@email.com");

        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("Name");
        itemDto.setDescription("Description");
        itemDto.setAvailable(true);
        itemDto.setOwner(userDto);
        itemDto.setLastBooking(null);
        itemDto.setNextBooking(null);
        itemDto.setComments(null);

        BookItemRequestDto requestDto = new BookItemRequestDto();
        requestDto.setItemId(itemDto.getId());
        requestDto.setStart(LocalDateTime.now());
        requestDto.setEnd(LocalDateTime.now().plusDays(1));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("state", BookingState.ALL);

        when(bookingClient.getBookingsOwner(userDto.getId(), parameters)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        mockMvc.perform(get("/bookings/1")
                .header(USER_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andDo(print()).andExpect(status().isOk());
    }
}
