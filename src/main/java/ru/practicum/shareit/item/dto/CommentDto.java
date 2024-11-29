package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDto {
    private Long id; // идентификатор комментария
    private String text; // Текст комментария
    private Item item; // Вещь, к которой относится комментарий
    private User author; // Автор комментария
    private LocalDateTime created; // Дата и время создания комментария
}
