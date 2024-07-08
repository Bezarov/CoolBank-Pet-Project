package com.coolbank.repository;

import com.coolbank.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findAllByFromAccountId(UUID accountId);
    List<Payment> findAllByToAccountId(UUID accountId);
    List<Payment> findAllByPaymentType(String paymentType);
    List<Payment> findAllByFromAccountIdAndPaymentDateBetween(UUID accountId,
                                                              LocalDateTime fromLocalDateTime,
                                                              LocalDateTime toLocalDateTime);

}
