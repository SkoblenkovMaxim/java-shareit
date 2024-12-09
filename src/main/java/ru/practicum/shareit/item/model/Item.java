package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.model.User;

@Entity
@Table(name = "items")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // id вещи

    private String name; // краткое название вещи

    private String description; // описание

    private Boolean available; // статус доступности

//    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private User owner; // id владельца вещи

    private Long requestId; // id запроса вещи, если она была создана по запросу другого пользователя
}
