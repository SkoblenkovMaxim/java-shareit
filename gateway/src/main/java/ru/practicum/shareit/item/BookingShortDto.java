package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingShortDto {

    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long bookerId;
}
