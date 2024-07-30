package com.coolbank.controller;

import com.coolbank.dto.CardDTO;
import com.coolbank.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cards")
public class CardController {
    private static final Logger logger = LoggerFactory.getLogger(CardController.class);
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/by-account-id/{accountId}")
    public ResponseEntity<CardDTO> createCard(@PathVariable UUID accountId) {
        logger.debug("Received POST request to create Card for Account with ID: {}", accountId);
        CardDTO cardDTO = cardService.createCard(accountId);
        return ResponseEntity.ok(cardDTO);
    }

    @GetMapping("/by-card-id/{cardId}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable UUID cardId) {
        logger.debug("Received GET request to get Card by ID: {}", cardId);
        CardDTO cardDTO = cardService.getCardById(cardId);
        return ResponseEntity.ok(cardDTO);
    }

    @GetMapping("/by-card-number/{cardNumber}")
    public ResponseEntity<CardDTO> getCardByCardNumber(@PathVariable String cardNumber) {
        logger.debug("Received GET request to get Card by Card Number: {}", cardNumber);
        CardDTO cardDTO = cardService.getCardByCardNumber(cardNumber);
        return ResponseEntity.ok(cardDTO);
    }

    @GetMapping("/by-user-name/{cardHolderFullName}")
    public ResponseEntity<List<CardDTO>> getCardsByCardHolderFullName(@PathVariable String cardHolderFullName) {
        logger.debug("Received GET request to get All Cards by Card Holder Name: {}", cardHolderFullName);
        List<CardDTO> cardDTOS = cardService.getCardsByCardHolderFullName(cardHolderFullName);
        return ResponseEntity.ok(cardDTOS);
    }

    @GetMapping("/by-account-id/{accountId}")
    public ResponseEntity<List<CardDTO>> getAllAccountCardsByAccountId(@PathVariable UUID accountId) {
        logger.debug("Received GET request to get All Cards by Account ID: {}", accountId);
        List<CardDTO> cardDTOS = cardService.getAllAccountCardsByAccountId(accountId);
        return ResponseEntity.ok(cardDTOS);
    }

    @GetMapping("/by-user-id/{holderId}")
    public ResponseEntity<List<CardDTO>> getAllUserCardsByCardHolderId(@PathVariable UUID holderId) {
        logger.debug("Received GET request to get All Cards by Card Holder ID: {}", holderId);
        List<CardDTO> cardDTOS = cardService.getAllUserCardsByCardHolderId(holderId);
        return ResponseEntity.ok(cardDTOS);
    }

    @GetMapping("/by-user-id/{holderId}/status")
    public ResponseEntity<List<CardDTO>> getAllUserCardsByStatus(@PathVariable UUID holderId,
                                                                 @RequestParam String status) {
        logger.debug("Received GET request to get All Cards by Card Holder ID: {}," +
                " with Status: {}", holderId, status);
        List<CardDTO> cardDTOS = cardService.getAllUserCardsByStatus(holderId, status);
        return ResponseEntity.ok(cardDTOS);
    }

    @GetMapping("/expired/by-user-id/{holderId}")
    public ResponseEntity<List<CardDTO>> getAllExpiredCard(@PathVariable UUID holderId) {
        logger.debug("Received GET request to get All Expired Cards by Card Holder ID: {}", holderId);
        List<CardDTO> cardDTOS = cardService.getAllExpiredCards(holderId);
        return ResponseEntity.ok(cardDTOS);
    }

    @GetMapping("/active/by-user-id/{holderId}")
    public ResponseEntity<List<CardDTO>> getAllActiveCards(@PathVariable UUID holderId) {
        logger.debug("Received GET request to get All Active Cards by Card Holder ID: {}", holderId);
        List<CardDTO> cardDTOS = cardService.getAllActiveCards(holderId);
        return ResponseEntity.ok(cardDTOS);
    }

    @PatchMapping("/by-card-id/{cardId}/status")
    public ResponseEntity<CardDTO> updateCardStatusById(@PathVariable UUID cardId,
                                                        @RequestParam String status) {
        logger.debug("Received PATCH request to update Status of Card by Card ID: {}," +
                " with Status: {}", cardId, status);
        CardDTO cardDTO = cardService.updateCardStatusById(cardId, status);
        return ResponseEntity.ok(cardDTO);
    }

    @PatchMapping("/by-card-number/{cardNumber}/status")
    public ResponseEntity<CardDTO> updateCardStatusByCardNumber(@PathVariable String cardNumber,
                                                                @RequestParam String status) {
        logger.debug("Received PATCH request to update Status of Card by " +
                "Card Card Number: {}, with Status: {}", cardNumber, status);
        CardDTO cardDTO = cardService.updateCardStatusByCardNumber(cardNumber, status);
        return ResponseEntity.ok(cardDTO);
    }

    @DeleteMapping("/by-card-id/{cardId}")
    public ResponseEntity<String> deleteCardById(@PathVariable UUID cardId) {
        logger.debug("Received DELETE request to remove Card with ID: {}", cardId);
        return cardService.deleteCardById(cardId);
    }

    @DeleteMapping("/by-account-id/{accountId}")
    public ResponseEntity<String> deleteAllAccountCardsByAccountId(@PathVariable UUID accountId) {
        logger.debug("Received DELETE request to remove All Account Cards" +
                " by Account ID: {}", accountId);
        return cardService.deleteAllAccountCardsByAccountId(accountId);
    }

    @DeleteMapping("/by-user-id/{cardHolderUUID}")
    public ResponseEntity<String> deleteAllUsersCardsByCardHolderUUID(@PathVariable UUID cardHolderUUID) {
        logger.debug("Received DELETE request to remove All User Cards" +
                " by Holder ID: {}", cardHolderUUID);
        return cardService.deleteAllUsersCardsByCardHolderUUID(cardHolderUUID);
    }


}
