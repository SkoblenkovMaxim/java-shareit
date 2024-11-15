package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {
    private Long id; // id вещи
    private String name; // краткое название вещи
    private String description; // описание
    private Boolean available; // статус доступности
    private Long ownerId; // id владельца вещи
    private Long requestId; // id запроса вещи, если она была создана по запросу другого пользователя
}
