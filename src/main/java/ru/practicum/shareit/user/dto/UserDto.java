package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long userId; // уникальный идентификатор
//    @NotBlank
    private String userName; // имя или логин пользователя
    @Email
//    @NotBlank
    private String email; // почта пользователя
}
