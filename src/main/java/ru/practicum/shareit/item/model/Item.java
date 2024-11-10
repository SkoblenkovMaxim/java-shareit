package ru.practicum.shareit.item.model;

/**
 * TODO Sprint add-controllers.
 */

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
    private Long itemId; // id вещи
    @NotBlank
    private String name; // краткое название вещи
    @Size(min = 1, max = 255, message = "Описание должно быть больше 1 и меньше 255 символов")
    private String description; // описание
    private Boolean available; // статус доступности
    private Long owner; // id владельца вещи
    private ItemRequest request; // id запроса вещи

    public Item(String name, String description, Boolean available, Long requestId) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.request = new ItemRequest();
    }
}
