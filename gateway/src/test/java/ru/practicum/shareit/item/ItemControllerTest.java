package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemClient itemClient;

    @Autowired
    private ObjectMapper mapper;

    private static final String USER_ID = "X-Sharer-User-Id";

    @Test
    void addItem() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("Name");
        userDto.setEmail("email@email.com");

        ItemDto itemDto = new ItemDto();
        itemDto.setName("Name");
        itemDto.setDescription("Description");
        itemDto.setAvailable(true);
        itemDto.setOwner(userDto);
        itemDto.setLastBooking(null);
        itemDto.setNextBooking(null);
        itemDto.setComments(null);

        when(itemClient.addItem(userDto.getId(), itemDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        mockMvc.perform(post("/items")
                .header(USER_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(itemDto))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getItemById() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("Name");
        userDto.setEmail("email@email.com");

        ItemDto itemDto = new ItemDto();
        itemDto.setName("Name");
        itemDto.setDescription("Description");
        itemDto.setAvailable(true);
        itemDto.setOwner(userDto);
        itemDto.setLastBooking(null);
        itemDto.setNextBooking(null);
        itemDto.setComments(null);

        when(itemClient.getItemById(itemDto.getId())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        mockMvc.perform(post("/items")
                .header(USER_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(itemDto))
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void addComment() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("Name");
        userDto.setEmail("email@email.com");

        ItemDto itemDto = new ItemDto();
        itemDto.setName("Name");
        itemDto.setDescription("Description");
        itemDto.setAvailable(true);
        itemDto.setOwner(userDto);
        itemDto.setLastBooking(null);
        itemDto.setNextBooking(null);
        itemDto.setComments(null);

        CommentDto commentDto = new CommentDto();
        commentDto.setText("Text");
        commentDto.setAuthorName(userDto.getName());
        commentDto.setItem(itemDto);
        commentDto.setAuthor(userDto);
        commentDto.setCreated(LocalDateTime.of(2024, 12, 20, 0, 0, 0));

        when(itemClient.addComment(userDto.getId(), itemDto.getId(), commentDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        mockMvc.perform(post("/items/1/comment")
                .header(USER_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commentDto))
        ).andDo(print()).andExpect(status().isOk());
    }
}
