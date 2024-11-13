package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long id; // уникальный идентификатор
    @NotBlank
    @NotNull
    private String name; // имя или логин пользователя
    @Email
    @NotBlank
    @NotNull
    private String email; // почта пользователя
}
