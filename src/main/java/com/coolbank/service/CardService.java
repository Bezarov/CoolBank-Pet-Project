package com.coolbank.service;

import com.coolbank.dto.CardDTO;
import com.coolbank.dto.UsersDTO;

import java.util.List;
import java.util.UUID;

public interface CardService {
    CardDTO createCard(UUID accountId, UsersDTO usersDTO);

    CardDTO getCardById(UUID cardId);

    CardDTO getCardByCardNumber(String cardNumber);

    List<CardDTO> getAllAccountCardsByAccountId(UUID accountId);

    List<CardDTO> getAllUserCardsByCardHolderId(UUID holderId);

    List<CardDTO> getAllUserCardsByStatus(UUID holderId, String status);

    void deleteCardById(UUID cardId);

    void deleteAllAccountCardsByAccountId(UUID accountId);

    void deleteAllUsersCardsByUserId(UUID userId);
}
