package com.coolbank.service;

import com.coolbank.dto.CardDTO;
import com.coolbank.dto.UsersDTO;
import com.coolbank.repository.AccountRepository;
import com.coolbank.repository.CardRepository;
import com.coolbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository,
                           AccountRepository accountRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }


    @Override
    public CardDTO createCard(UUID accountId, UsersDTO usersDTO) {
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
    public void deleteCardById(UUID cardId) {

    }

    @Override
    public void deleteAllAccountCardsByAccountId(UUID accountId) {

    }

    @Override
    public void deleteAllUsersCardsByUserId(UUID userId) {

    }
}
