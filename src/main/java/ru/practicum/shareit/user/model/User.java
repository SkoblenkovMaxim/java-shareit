package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Long id; // уникальный идентификатор
    @NotBlank
    @NotNull
    private String name; // имя или логин пользователя
    @Email
    @NotBlank
    @NotNull
    private String email; // почта пользователя
}
