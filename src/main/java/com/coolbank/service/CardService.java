package com.coolbank.service;

import com.coolbank.dto.CardDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CardService {
    ResponseEntity<String> createCard(UUID accountId, String cardHolderFullName);

    CardDTO getCardById(UUID cardId);

    CardDTO getCardByCardNumber(String cardNumber);
    CardDTO getCardByCardHolderFullName(String cardHolderFullName);

    List<CardDTO> getAllAccountCardsByAccountId(UUID accountId);

    List<CardDTO> getAllUserCardsByCardHolderId(UUID holderId);

    List<CardDTO> getAllUserCardsByStatus(UUID holderId, String status);
    List<CardDTO> getAllExpiredCard(UUID holderId);
    List<CardDTO> getAllNotExpiredCard(UUID holderId);

    void deleteCardById(UUID cardId);

    void deleteAllAccountCardsByAccountId(UUID accountId);

    void deleteAllUsersCardsByCardHolderUUID(UUID cardHolderUUID);
}
