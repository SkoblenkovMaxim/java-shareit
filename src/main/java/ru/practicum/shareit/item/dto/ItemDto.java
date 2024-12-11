package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.io.Serializable;
import java.util.List;

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
    @Transient
    private UserDto owner;
    private Long requestId; // id запроса вещи
    @Transient
    private BookingShortDto lastBooking;
    @Transient
    private BookingShortDto nextBooking;
    private List<CommentDto> comments;
}
