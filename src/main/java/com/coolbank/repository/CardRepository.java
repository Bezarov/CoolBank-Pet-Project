package com.coolbank.repository;

import com.coolbank.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    Optional<Card> findByCardNumber(String cardNumber);

    List<Card> findAllByCardHolderFullName(String cardHolderFullName);

    List<Card> findAllByAccountId(UUID accountId);

    List<Card> findAllByCardHolderUUID(UUID holderId);

    List<Card> findAllByStatus(String status);

    void deleteAllByAccountId(UUID accountId);

    void deleteAllByCardHolderUUID(UUID cardHolderUUID);
}
