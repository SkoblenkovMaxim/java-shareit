package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto {
    private Long id; // id вещи
//    @NotBlank
    private String name; // краткое название вещи
//    @NotBlank
//    @Size(min = 1, max = 255, message = "Описание должно быть больше 1 и меньше 255 символов")
    private String description; // описание
//    @NotBlank
    private Boolean available; // статус доступности
    private Long requestId; // id запроса вещи
}
