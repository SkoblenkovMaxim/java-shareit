package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto implements Serializable {
    private Long id; // id вещи
    private String name; // краткое название вещи
    private String description; // описание
    private Boolean available; // статус доступности
    @Transient
    private UserDto owner;
    private Long requestId; // id запроса вещи
    @Transient
    private BookingShortDto lastBooking;
    @Transient
    private BookingShortDto nextBooking;
    @Transient
    private List<CommentDto> comments;
}
