package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto implements Serializable {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Status status;
    @Transient
    private UserDto booker;
    private ItemDto item;
}
