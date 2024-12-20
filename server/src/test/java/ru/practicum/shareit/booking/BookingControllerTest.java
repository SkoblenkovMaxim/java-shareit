package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingInputDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = BookingController.class)
public class BookingControllerTest {

    @MockBean
    private BookingService bookingService;
    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private BookingController bookingController;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;
    private BookingDto bookingDto;
    private BookingInputDto bookingInputDto;

    private static final String USER_ID = "X-Sharer-User-Id";

    UserDto userDto = new UserDto(
            1L,
            "TestName",
            "test@email.ru"
    );

    ItemDto itemDto = new ItemDto(
            1L,
            "TestName",
            "TestDescription",
            true,
            userDto,
            null,
            null,
            null,
            null
    );

    private List<BookingDto> listBookingDto = new ArrayList<>();

    @BeforeEach
    public void setUp() throws Exception {
        bookingDto = new BookingDto(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                Status.APPROVED,
                userDto,
                itemDto
        );

        bookingInputDto = new BookingInputDto(
                itemDto.getId(),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1)
        );
    }

    @Test
    void addBooking() throws Exception {
        when(bookingService.bookItem(bookingInputDto, userDto.getId())).thenReturn(bookingDto);

        mvc.perform(post("/bookings")
                .header(USER_ID, 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bookingDto))).andDo(print())
                .andExpect(status().isOk());
//                .andExpect((ResultMatcher) jsonPath("$.id", is(bookingDto.getId()), Long.class))
//                .andExpect((ResultMatcher) jsonPath("$.start",
//                        is(bookingDto.getStart().format(DateTimeFormatter.ISO_DATE_TIME))))
//                .andExpect((ResultMatcher) jsonPath("$.end",
//                        is(bookingDto.getEnd().format(DateTimeFormatter.ISO_DATE_TIME))))
//                .andExpect((ResultMatcher) jsonPath("$.status", is(bookingDto.getStatus().toString())));
    }

    @Test
    void getBookings() throws Exception {
        when(bookingService.getBookings(any(String.class), any(Long.class)))
                .thenReturn(List.of(bookingDto));

        mvc.perform(get("/bookings")
                        .content(mapper.writeValueAsString(listBookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(USER_ID, 1)).andDo(print())
                .andExpect(status().isOk());
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect((ResultMatcher) jsonPath("$.[0].id", is(bookingDto.getId()), Long.class))
//                .andExpect((ResultMatcher) jsonPath("$.[0].start", is(bookingDto.getStart()
//                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
//                .andExpect((ResultMatcher) jsonPath("$.[0].end", is(bookingDto.getEnd()
//                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
//                .andExpect((ResultMatcher) jsonPath("$.[0].item.id", is(bookingDto.getItem().getId()), Long.class))
//                .andExpect((ResultMatcher) jsonPath("$.[0].booker.id", is(bookingDto.getBooker().getId()), Long.class))
//                .andExpect((ResultMatcher) jsonPath("$.[0].status", is(bookingDto.getStatus().toString())));
    }

    @Test
    void getBookingById() throws Exception {
        when(bookingService.getBookingById(any(Long.class), any(Long.class)))
                .thenReturn(bookingDto);

        mvc.perform(get("/bookings/1")
                        .content(mapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(USER_ID, 1))
                .andExpect(status().isOk());
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect((ResultMatcher) jsonPath("$.id", is(bookingDto.getId()), Long.class))
//                .andExpect((ResultMatcher) jsonPath("$.start",
//                        is(bookingDto.getStart().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
//                .andExpect((ResultMatcher) jsonPath("$.end",
//                        is(bookingDto.getEnd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
//                .andExpect((ResultMatcher) jsonPath("$.status", is(bookingDto.getStatus().toString()), Status.class));
    }

    @Test
    void updateBooking() throws Exception {
        when(bookingService.update(any(Long.class), any(Long.class), any()))
                .thenReturn(bookingDto);
        mvc.perform(patch("/bookings/1")
                        .content(mapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(USER_ID, 1)
                        .queryParam("approved", "true"))
                .andExpect(status().isOk());
//                .andExpect((ResultMatcher) jsonPath("$.id", is(bookingDto.getId()), Long.class))
//                .andExpect((ResultMatcher) jsonPath("$.start",
//                        is(bookingDto.getStart().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
//                .andExpect((ResultMatcher) jsonPath("$.end",
//                        is(bookingDto.getEnd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
//                .andExpect((ResultMatcher) jsonPath("$.status", is(bookingDto.getStatus().toString())));
    }

    @Test
    void getBookingsByOwner() throws Exception {
        when(bookingService.getBookingsOwner(any(String.class), any(Long.class)))
                .thenReturn(List.of(bookingDto));

        mvc.perform(get("/bookings/owner?from=0&size=10")
                        .content(mapper.writeValueAsString(listBookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(USER_ID, 1))
                .andExpect(status().isOk());
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect((ResultMatcher) jsonPath("$.[0].id", is(bookingDto.getId()), Long.class))
//                .andExpect((ResultMatcher) jsonPath("$.[0].start", is(bookingDto.getStart()
//                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
//                .andExpect((ResultMatcher) jsonPath("$.[0].end", is(bookingDto.getEnd()
//                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
//                .andExpect((ResultMatcher) jsonPath("$.[0].item.id", is(bookingDto.getItem().getId()), Long.class))
//                .andExpect((ResultMatcher) jsonPath("$.[0].booker.id", is(bookingDto.getBooker().getId()), Long.class))
//                .andExpect((ResultMatcher) jsonPath("$.[0].status", is(bookingDto.getStatus().toString())));
    }


}
