package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemClient itemClient;

    private static final String owner = "X-Sharer-User-Id";

    public ItemController(ItemClient itemClient) {
        this.itemClient = itemClient;
    }

    @PostMapping
    public ResponseEntity<Object> addItem(@Valid @RequestHeader(owner) Long userId,
                                          @Valid @RequestBody ItemDto itemDto) {
        log.info("Получен запрос на добавление вещи пользователем с ownerId: {}", userId);
        return itemClient.addItem(userId, itemDto);
    }

    //    @PatchMapping("/{itemId}")
//    public ResponseEntity<Object> updateItem(
//             @RequestBody ItemDto itemDto,
//            @Valid @RequestHeader(owner) Long ownerId,
//            @Valid @PathVariable Long itemId) {
//        return itemClient.update(itemDto, ownerId, itemId);
//    }
//
//    @GetMapping
//    public ResponseEntity<Object> getItemsByOwner(@RequestHeader(owner) Long ownerId) {
//        return itemClient.getItemsByOwner(ownerId);
//    }
//
//    @DeleteMapping("/{itemId}")
//    public void deleteItem(@RequestHeader(owner) Long ownerId, @PathVariable Long itemId) {
//        itemClient.deleteItem(ownerId, itemId);
//    }
//
    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@Valid @PathVariable Long itemId) {
        return itemClient.getItemById(itemId);
    }
    //
//    @GetMapping("/search")
//    public ResponseEntity<Object> getItemsBySearchQuery(@RequestParam String text) {
//        return itemClient.getItemsBySearchQuery(text);
//    }
//
    @ResponseBody
    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader(owner) Long userId,
                                             @PathVariable Long itemId,
                                             @RequestBody CommentDto commentDto) {
        log.info("Получен запрос на добавление отзыва пользователем");
        return itemClient.addComment(userId, itemId, commentDto);
    }

}
