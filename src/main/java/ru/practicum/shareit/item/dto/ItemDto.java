package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto {
    private Long id; // id вещи
    private String name; // краткое название вещи
    private String description; // описание
    private Boolean available; // статус доступности
    private Long requestId; // id запроса вещи
}
