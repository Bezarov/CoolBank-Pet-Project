package com.coolbank.controller;

import com.coolbank.dto.CardDTO;
import com.coolbank.service.CardServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/card")
public class CardController {
    private final CardServiceImpl cardService;

    public CardController(CardServiceImpl cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/register/{accountId}/{cardHolderFullName}")
    public ResponseEntity<CardDTO> createCard(@PathVariable UUID accountId,
                                             @PathVariable String cardHolderFullName) {
        return ResponseEntity.ok(cardService.createCard(accountId, cardHolderFullName));
    }

    @GetMapping("/id/{cardId}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable UUID cardId) {
        CardDTO cardDTO = cardService.getCardById(cardId);
        return ResponseEntity.ok(cardDTO);
    }

    @GetMapping("/number/{cardNumber}")
    public ResponseEntity<CardDTO> getCardByCardNumber(@PathVariable String cardNumber) {
        CardDTO cardDTO = cardService.getCardByCardNumber(cardNumber);
        return ResponseEntity.ok(cardDTO);
    }

    @GetMapping("/name/{cardHolderFullName}")
    public ResponseEntity<List<CardDTO>> getCardsByCardHolderFullName(@PathVariable String cardHolderFullName) {
        List<CardDTO> cards = cardService.getCardsByCardHolderFullName(cardHolderFullName);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<CardDTO>> getAllAccountCardsByAccountId(@PathVariable UUID accountId) {
        List<CardDTO> cardDTOS = cardService.getAllAccountCardsByAccountId(accountId);
        return ResponseEntity.ok(cardDTOS);
    }

    @GetMapping("/holder/{holderId}")
    public ResponseEntity<List<CardDTO>> getAllUserCardsByCardHolderId(@PathVariable UUID holderId) {
        List<CardDTO> cardDTOS = cardService.getAllUserCardsByCardHolderId(holderId);
        return ResponseEntity.ok(cardDTOS);
    }

    @GetMapping("/holder/{holderId}/status/{status}")
    public ResponseEntity<List<CardDTO>> getAllUserCardsByStatus(@PathVariable UUID holderId,
                                                                 @PathVariable String status) {
        List<CardDTO> cardDTOS = cardService.getAllUserCardsByStatus(holderId, status);
        return ResponseEntity.ok(cardDTOS);
    }

    @GetMapping("/expired/holder/{holderId}")
    public ResponseEntity<List<CardDTO>> getAllExpiredCard(@PathVariable UUID holderId) {
        List<CardDTO> cardDTOS = cardService.getAllExpiredCards(holderId);
        return ResponseEntity.ok(cardDTOS);
    }

    @GetMapping("/active/holder/{holderId}")
    public ResponseEntity<List<CardDTO>> getAllActiveCards(@PathVariable UUID holderId) {
        List<CardDTO> cardDTOS = cardService.getAllActiveCards(holderId);
        return ResponseEntity.ok(cardDTOS);
    }

    @PatchMapping("/id/{cardId}/status/{status}")
    public ResponseEntity<CardDTO> updateCardStatusById(@PathVariable UUID cardId, @PathVariable String status) {
        CardDTO cardDTO = cardService.updateCardStatusById(cardId, status);
        return ResponseEntity.ok(cardDTO);
    }

    @PatchMapping("/number/{cardNumber}/status/{status}")
    public ResponseEntity<CardDTO> updateCardStatusByCardNumber(@PathVariable String cardNumber,
                                                                @PathVariable String status) {
        CardDTO cardDTO = cardService.updateCardStatusByCardNumber(cardNumber, status);
        return ResponseEntity.ok(cardDTO);
    }

    @DeleteMapping("/id/{cardId}")
    public ResponseEntity<String> deleteCardById(@PathVariable UUID cardId) {
        return cardService.deleteCardById(cardId);
    }

    @DeleteMapping("/account/{accountId}")
    public ResponseEntity<String> deleteAllAccountCardsByAccountId(@PathVariable UUID accountId) {
        return cardService.deleteAllAccountCardsByAccountId(accountId);
    }

    @DeleteMapping("/holder/{cardHolderUUID}")
    public ResponseEntity<String> deleteAllUsersCardsByCardHolderUUID(@PathVariable UUID cardHolderUUID) {
        return cardService.deleteAllUsersCardsByCardHolderUUID(cardHolderUUID);
    }


}
