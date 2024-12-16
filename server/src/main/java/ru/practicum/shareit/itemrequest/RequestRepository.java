package ru.practicum.shareit.itemrequest;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.itemrequest.model.ItemRequest;

import java.util.List;

public interface RequestRepository extends JpaRepository<ItemRequest, Long> {

    List<ItemRequest> findByRequestorId(Long userId);

}
