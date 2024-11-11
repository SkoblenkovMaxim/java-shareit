package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;

/**
 * TODO Sprint add-controllers.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto {
    private Long itemId; // id вещи
    @NotBlank
    private String name; // краткое название вещи
    @NotBlank
    @Size(min = 1, max = 255, message = "Описание должно быть больше 1 и меньше 255 символов")
    private String description; // описание
    @NotBlank
    private Boolean available; // статус доступности
    private Long requestId; // id запроса вещи

    public ItemDto(String name, String description, Boolean available, Long ownerId, Long requestId) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.requestId = requestId;
    }
}
