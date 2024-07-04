package com.coolbank.service;

import com.coolbank.dto.CardDTO;

import java.util.List;
import java.util.UUID;

public interface CardService {
    CardDTO createCard(UUID accountId, CardDTO cardDTO);

    CardDTO getCardById(UUID cardId);

    CardDTO getCardByCardNumber(String cardNumber);
    CardDTO getCardByCardHolderFullName(String cardHolderFullName);

    List<CardDTO> getAllAccountCardsByAccountId(UUID accountId);

    List<CardDTO> getAllUserCardsByCardHolderId(UUID holderId);

    List<CardDTO> getAllUserCardsByStatus(UUID holderId, String status);
    List<CardDTO> getAllExpiredCard(UUID holderID);
    List<CardDTO> getAllNotExpiredCard(UUID holderID);

    void deleteCardById(UUID cardId);

    void deleteAllAccountCardsByAccountId(UUID accountId);

    void deleteAllUsersCardsByUserId(UUID userId);
}
