package ru.practicum.shareit.booking;

import jakarta.persistence.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDateTime start;

    @Column(name = "end_date")
    private LocalDateTime end;

    @ManyToOne
    @Column(name = "item_id")
    private Item item;

    @ManyToOne
    @Column(name = "booker_id")
    private User booker;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
}
