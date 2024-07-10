package com.coolbank.service;

import com.coolbank.dto.CardDTO;
import com.coolbank.model.Card;
import com.coolbank.repository.AccountRepository;
import com.coolbank.repository.CardRepository;
import com.coolbank.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final UsersRepository usersRepository;
    private final Random random = new Random();

    @Autowired
    public CardServiceImpl(CardRepository cardRepository,
                           AccountRepository accountRepository, UsersRepository usersRepository) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
        this.usersRepository = usersRepository;
    }

    private CardDTO convertCardModelToDTO(Card card) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(card.getId());
        cardDTO.setCardNumber(card.getCardNumber());
        cardDTO.setCardHolderFullName(card.getCardHolderFullName());
        cardDTO.setExpirationDate(card.getExpirationDate());
        cardDTO.setCvv(card.getCvv());
        cardDTO.setAccount(card.getAccount());
        cardDTO.setStatus(card.getStatus());
        return cardDTO;
    }

    private Card cardGenerator(UUID accountId, String cardHolderFullName) {
        Card card = new Card();
        card.setAccount(accountRepository.findById(accountId).orElseThrow(() ->
                new RuntimeException("Account ID Not Found in Database")));
        card.setCardNumber(cardNumberGenerator());
        card.setCardHolderFullName(cardHolderFullName);
        card.setExpirationDate(LocalDate.now().plusYears(random.nextInt(5) + 1));
        card.setCvv(String.format("%03d", random.nextInt(1000)));
        card.setStatus("ACTIVE");
        return card;
    }

    private String cardNumberGenerator() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            cardNumber.append(String.format("%04d", random.nextInt(10000)));
            if (i < 3) {
                cardNumber.append(" ");
            }
        }
        return cardNumber.toString();
    }

    @Override
    public ResponseEntity<String> createCard(UUID accountId, String cardHolderFullName) {
        accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + accountId));
        cardRepository.save(cardGenerator(accountId, cardHolderFullName));
        return new ResponseEntity<>("Card created successfully", HttpStatus.CREATED);
    }

    @Override
    public CardDTO getCardById(UUID cardId) {
        return cardRepository.findById(cardId)
                .map(this::convertCardModelToDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Card with such ID was NOT Found" + cardId));
    }

    @Override
    public CardDTO getCardByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber)
                .map(this::convertCardModelToDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Card with such Card Number was NOT Found" + cardNumber));
    }

    @Override
    public CardDTO getCardByCardHolderFullName(String cardHolderFullName) {
        return cardRepository.findByCardHolderFullName(cardHolderFullName)
                .map(this::convertCardModelToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Card with such Card Holder Full Name was NOT Found" + cardHolderFullName));
    }

    @Override
    public List<CardDTO> getAllAccountCardsByAccountId(UUID accountId) {
        accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + accountId));

        List<Card> cards = cardRepository.findAllByAccountId(accountId);
        return cards.stream()
                .map(this::convertCardModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CardDTO> getAllUserCardsByCardHolderId(UUID holderId) {
        usersRepository.findById(holderId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + holderId));
        List<Card> cards = cardRepository.findAllByCardHolderUUID(holderId);
        return cards.stream()
                .map(this::convertCardModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CardDTO> getAllUserCardsByStatus(UUID holderId, String status) {
        usersRepository.findById(holderId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + holderId));
        List<Card> cards = cardRepository.findAllByStatus(status);
        return cards.stream()
                .map(this::convertCardModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CardDTO> getAllExpiredCard(UUID holderId) {
        usersRepository.findById(holderId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + holderId));
        List<Card> cards = cardRepository.findAllByCardHolderUUID(holderId);
        return cards.stream()
                .filter(card -> card.getExpirationDate().isBefore(LocalDate.now()))
                .map(this::convertCardModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CardDTO> getAllNotExpiredCard(UUID holderId) {
        usersRepository.findById(holderId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + holderId));
        List<Card> cards = cardRepository.findAllByCardHolderUUID(holderId);
        return cards.stream()
                .filter(card -> card.getExpirationDate().isAfter(LocalDate.now()))
                .map(this::convertCardModelToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteCardById(UUID cardId) {
        cardRepository.findById(cardId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Card with such ID was NOT Found" + cardId));
        cardRepository.deleteById(cardId);
    }

    @Transactional
    @Override
    public void deleteAllAccountCardsByAccountId(UUID accountId) {
        accountRepository.findById(accountId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + accountId));
        cardRepository.deleteAllByAccountId(accountId);
    }

    @Transactional
    @Override
    public void deleteAllUsersCardsByCardHolderUUID(UUID cardHolderUUID) {
        usersRepository.findById(cardHolderUUID).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + cardHolderUUID));
        cardRepository.deleteAllByCardHolderUUID(cardHolderUUID);
    }
}
