-- Init users
INSERT INTO "users" (id, name, email, phone_number, address) VALUES
('1e8c6265-1c1d-4e89-8d5a-9f776de0842e', 'John Doe', 'john.doe@example.com', '1234567890', '123 Main St'),
('e6f7008c-758b-4a3a-8b7d-7d0d5f638b2f', 'Jane Smith', 'jane.smith@example.com', '0987654321', '456 Elm St');
-- Init accounts
INSERT INTO account (id, balance, currency, user_id) VALUES
('8a73b8b1-45f4-4c82-9f2d-9e4b2e3a3b1a', 1000.00, 'USD', '1e8c6265-1c1d-4e89-8d5a-9f776de0842e'),
('9f22b47e-9237-4d4e-b0ff-7d8d7a0f15d7', 2500.00, 'EUR', 'e6f7008c-758b-4a3a-8b7d-7d0d5f638b2f');
-- Init cards
INSERT INTO card (id, card_number, expiration_date, cvv, account_id) VALUES
('f34b3d7c-3c39-4c9e-bc75-3d7d3e9f2f1b', '1111-2222-3333-4444', '2025-12-31', '123', '8a73b8b1-45f4-4c82-9f2d-9e4b2e3a3b1a'),
('fc96d7bc-5939-4e1f-a7a6-5d7d7e3a3b2a', '5555-6666-7777-8888', '2026-11-30', '456', '9f22b47e-9237-4d4e-b0ff-7d8d7a0f15d7');
--Init payments
INSERT INTO payment (id, amount, payment_date, payment_type, status, from_account_id, to_account_id) VALUES
('c2f81f9d-6d7f-4f1e-bc1d-7d8d3e9f2a1b', 200.00, '2023-06-24T10:00:00', 'TRANSFER', 'COMPLETED', '8a73b8b1-45f4-4c82-9f2d-9e4b2e3a3b1a', '9f22b47e-9237-4d4e-b0ff-7d8d7a0f15d7'),
('a7d9d9d1-4e7f-4d1e-8c1d-7d8d3e9f2a2c', 150.00, '2023-06-25T11:30:00', 'TRANSFER', 'PENDING', '9f22b47e-9237-4d4e-b0ff-7d8d7a0f15d7', '8a73b8b1-45f4-4c82-9f2d-9e4b2e3a3b1a');