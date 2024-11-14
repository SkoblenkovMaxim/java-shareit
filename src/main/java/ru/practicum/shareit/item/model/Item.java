package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {
    private Long id; // id вещи
//    @NotBlank
    private String name; // краткое название вещи
    @Size(min = 1, max = 255, message = "Описание должно быть больше 1 и меньше 255 символов")
    private String description; // описание
    private Boolean available; // статус доступности
    private Long ownerId; // id владельца вещи
    private Long requestId; // id запроса вещи, если она была создана по запросу другого пользователя
}
