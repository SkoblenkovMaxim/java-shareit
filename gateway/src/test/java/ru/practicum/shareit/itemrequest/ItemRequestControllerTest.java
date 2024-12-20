package ru.practicum.shareit.itemrequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.itemrequest.dto.ItemRequestDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemRequestController.class)
public class ItemRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRequestClient itemRequestClient;

    @Autowired
    private ObjectMapper mapper;

    private static final String USER_ID = "X-Sharer-User-Id";

    @Test
    void createItemRequest() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Name");
        userDto.setEmail("email@email.com");

        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setId(1L);
        requestDto.setDescription("Test description");
        requestDto.setRequestorId(userDto.getId());
        requestDto.setCreated(LocalDateTime.of(2024, 12, 24, 10, 0, 0));

        when(itemRequestClient.createItemRequest(requestDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        mockMvc.perform(post("/requests")
                .header(USER_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getUserRequests() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Name");
        userDto.setEmail("email@email.com");

        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setId(1L);
        requestDto.setDescription("Test description");
        requestDto.setRequestorId(1L);
        requestDto.setCreated(LocalDateTime.of(2024, 12, 24, 10, 0, 0));

        when(itemRequestClient.getUserRequests(userDto.getId())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        mockMvc.perform(post("/requests")
                .header(USER_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void findByItemRequestId() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Name");
        userDto.setEmail("email@email.com");

        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setId(1L);
        requestDto.setDescription("Test description");
        requestDto.setRequestorId(1L);
        requestDto.setCreated(LocalDateTime.of(2024, 12, 24, 10, 0, 0));

        when(itemRequestClient.findByItemRequestId(requestDto.getId(), userDto.getId()))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        mockMvc.perform(post("/requests")
                .header(USER_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto))
        ).andDo(print()).andExpect(status().isOk());
    }
}
