package com.coolbank.service;

import com.coolbank.dto.CardDTO;
import com.coolbank.model.Card;
import com.coolbank.repository.AccountRepository;
import com.coolbank.repository.CardRepository;
import com.coolbank.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository,
                           AccountRepository accountRepository, UsersRepository usersRepository) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
        this.usersRepository = usersRepository;
    }

    private CardDTO convertCardModelToDTO(UUID accountId, Card card) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(card.getCardNumber());
        cardDTO.setCardHolderFullName(card.getCardHolderFullName());
        cardDTO.setExpirationDate(card.getExpirationDate());
        cardDTO.setCvv(card.getCvv());
        cardDTO.setStatus(card.getStatus());
        cardDTO.setAccount(accountRepository.findById(accountId).orElseThrow(() ->
                new RuntimeException("Account ID Not Found in Database")));
        return cardDTO;
    }

    @Override
    public ResponseEntity<String> createCard(CardDTO cardDTO) {
        return null;
    }

    @Override
    public CardDTO getCardById(UUID cardId) {
        return null;
    }

    @Override
    public CardDTO getCardByCardNumber(String cardNumber) {
        return null;
    }

    @Override
    public CardDTO getCardByCardHolderFullName(String cardHolderFullName) {
        return null;
    }

    @Override
    public List<CardDTO> getAllAccountCardsByAccountId(UUID accountId) {
        return null;
    }

    @Override
    public List<CardDTO> getAllUserCardsByCardHolderId(UUID holderId) {
        return null;
    }

    @Override
    public List<CardDTO> getAllUserCardsByStatus(UUID holderId, String status) {
        return null;
    }

    @Override
    public List<CardDTO> getAllExpiredCard(UUID holderID) {
        return null;
    }

    @Override
    public List<CardDTO> getAllNotExpiredCard(UUID holderID) {
        return null;
    }

    @Override
    public void deleteCardById(UUID cardId) {

    }

    @Override
    public void deleteAllAccountCardsByAccountId(UUID accountId) {

    }

    @Override
    public void deleteAllUsersCardsByUserId(UUID userId) {

    }
}
