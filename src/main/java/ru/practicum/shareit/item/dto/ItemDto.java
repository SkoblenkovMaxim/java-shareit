package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto {
    private Long id; // id вещи
    @NotNull
    @NotBlank
    private String name; // краткое название вещи
    @NotNull
    @NotBlank
    private String description; // описание
    @NotNull
    private Boolean available; // статус доступности
    private Long requestId; // id запроса вещи
}
