package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.model.User;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto implements Serializable {
    private Long id; // id вещи
    @NotNull
    @NotBlank
    private String name; // краткое название вещи
    @NotNull
    @NotBlank
    private String description; // описание
    @NotNull
    private Boolean available; // статус доступности
//    @JsonIgnore
//    @NotNull
    private User owner;
    private Long requestId; // id запроса вещи
}
