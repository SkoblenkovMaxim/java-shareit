package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long id; // уникальный идентификатор
    private String name; // имя или логин пользователя
    @Email
    private String email; // почта пользователя
}
