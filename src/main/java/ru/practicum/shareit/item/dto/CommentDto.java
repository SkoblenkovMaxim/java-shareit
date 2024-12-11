package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDto implements Serializable {
    private Long id; // идентификатор комментария
    private String text; // Текст комментария
    private String authorName;
    @Transient
    private Item item; // Вещь, к которой относится комментарий
    @Transient
    private UserDto author; // Автор комментария
    private LocalDateTime created; // Дата и время создания комментария
}
