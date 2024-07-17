package com.coolbank.controller;

import com.coolbank.dto.CardDTO;
import com.coolbank.service.CardServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/card")
public class CardController {
    private static final Logger logger = LoggerFactory.getLogger(CardController.class);
    private final CardServiceImpl cardService;

    public CardController(CardServiceImpl cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/register/{accountId}/{cardHolderFullName}")
    public ResponseEntity<CardDTO> createCard(@PathVariable UUID accountId,
                                             @PathVariable String cardHolderFullName) {
        logger.debug("Received POST request to create Card for Account with ID: {}," +
                " And Card Holder Name: {}", accountId, cardHolderFullName);
        return ResponseEntity.ok(cardService.createCard(accountId, cardHolderFullName));
    }

    @GetMapping("/id/{cardId}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable UUID cardId) {
        logger.debug("Received GET request to get Card by ID: {}", cardId);
        CardDTO cardDTO = cardService.getCardById(cardId);
        return ResponseEntity.ok(cardDTO);
    }

    @GetMapping("/number/{cardNumber}")
    public ResponseEntity<CardDTO> getCardByCardNumber(@PathVariable String cardNumber) {
        logger.debug("Received GET request to get Card by Card Number: {}", cardNumber);
        CardDTO cardDTO = cardService.getCardByCardNumber(cardNumber);
        return ResponseEntity.ok(cardDTO);
    }

    @GetMapping("/name/{cardHolderFullName}")
    public ResponseEntity<List<CardDTO>> getCardsByCardHolderFullName(@PathVariable String cardHolderFullName) {
        logger.debug("Received GET request to get All Cards by Card Holder Name: {}", cardHolderFullName);
        List<CardDTO> cards = cardService.getCardsByCardHolderFullName(cardHolderFullName);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<CardDTO>> getAllAccountCardsByAccountId(@PathVariable UUID accountId) {
        logger.debug("Received GET request to get All Cards by Account ID: {}", accountId);
        List<CardDTO> cardDTOS = cardService.getAllAccountCardsByAccountId(accountId);
        return ResponseEntity.ok(cardDTOS);
    }

    @GetMapping("/holder/{holderId}")
    public ResponseEntity<List<CardDTO>> getAllUserCardsByCardHolderId(@PathVariable UUID holderId) {
        logger.debug("Received GET request to get All Cards by Card Holder ID: {}", holderId);
        List<CardDTO> cardDTOS = cardService.getAllUserCardsByCardHolderId(holderId);
        return ResponseEntity.ok(cardDTOS);
    }

    @GetMapping("/holder/{holderId}/status/{status}")
    public ResponseEntity<List<CardDTO>> getAllUserCardsByStatus(@PathVariable UUID holderId,
                                                                 @PathVariable String status) {
        logger.debug("Received GET request to get All Cards by Card Holder ID: {}," +
                " with Status: {}", holderId, status);
        List<CardDTO> cardDTOS = cardService.getAllUserCardsByStatus(holderId, status);
        return ResponseEntity.ok(cardDTOS);
    }

    @GetMapping("/expired/holder/{holderId}")
    public ResponseEntity<List<CardDTO>> getAllExpiredCard(@PathVariable UUID holderId) {
        logger.debug("Received GET request to get All Expired Cards by Card Holder ID: {}", holderId);
        List<CardDTO> cardDTOS = cardService.getAllExpiredCards(holderId);
        return ResponseEntity.ok(cardDTOS);
    }

    @GetMapping("/active/holder/{holderId}")
    public ResponseEntity<List<CardDTO>> getAllActiveCards(@PathVariable UUID holderId) {
        logger.debug("Received GET request to get All Active Cards by Card Holder ID: {}", holderId);
        List<CardDTO> cardDTOS = cardService.getAllActiveCards(holderId);
        return ResponseEntity.ok(cardDTOS);
    }

    @PatchMapping("/id/{cardId}/status/{status}")
    public ResponseEntity<CardDTO> updateCardStatusById(@PathVariable UUID cardId,
                                                        @PathVariable String status) {
        logger.debug("Received PATCH request to update Status of Card by Card ID: {}," +
                " with Status: {}", cardId, status);
        CardDTO cardDTO = cardService.updateCardStatusById(cardId, status);
        return ResponseEntity.ok(cardDTO);
    }

    @PatchMapping("/number/{cardNumber}/status/{status}")
    public ResponseEntity<CardDTO> updateCardStatusByCardNumber(@PathVariable String cardNumber,
                                                                @PathVariable String status) {
        logger.debug("Received PATCH request to update Status of Card by " +
                "Card Card Number: {}, with Status: {}", cardNumber, status);
        CardDTO cardDTO = cardService.updateCardStatusByCardNumber(cardNumber, status);
        return ResponseEntity.ok(cardDTO);
    }

    @DeleteMapping("/id/{cardId}")
    public ResponseEntity<String> deleteCardById(@PathVariable UUID cardId) {
        logger.debug("Received DELETE request to remove Card with ID: {}", cardId);
        return cardService.deleteCardById(cardId);
    }

    @DeleteMapping("/account/{accountId}")
    public ResponseEntity<String> deleteAllAccountCardsByAccountId(@PathVariable UUID accountId) {
        logger.debug("Received DELETE request to remove All Account Cards" +
                " by Account ID: {}", accountId);
        return cardService.deleteAllAccountCardsByAccountId(accountId);
    }

    @DeleteMapping("/holder/{cardHolderUUID}")
    public ResponseEntity<String> deleteAllUsersCardsByCardHolderUUID(@PathVariable UUID cardHolderUUID) {
        logger.debug("Received DELETE request to remove All User Cards" +
                " by Holder ID: {}", cardHolderUUID);
        return cardService.deleteAllUsersCardsByCardHolderUUID(cardHolderUUID);
    }


}
