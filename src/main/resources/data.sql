-- Users
INSERT INTO users (id, created_date, email, first_name, last_name, password, phone_number, status) VALUES
('1e8c6265-1c1d-4e89-8d5a-9f776de0842e', CURRENT_TIMESTAMP, 'john.doe@example.com', 'John', 'Doe', 'password', '1234567890', 'ACTIVE'),
('e6f7008c-758b-4a3a-8b7d-7d0d5f638b2f', CURRENT_TIMESTAMP, 'jane.smith@example.com', 'Jane', 'Smith', 'password', '0987654321', 'ACTIVE'),
('d4b7b4f3-9d8a-4f9e-8e2c-9f776de0843e', CURRENT_TIMESTAMP, 'alice.jones@example.com', 'Alice', 'Jones', 'password', '1122334455', 'ACTIVE');

-- Accounts for John Doe
INSERT INTO account (id, balance, created_date, users_id, account_holder_name, account_number, account_type, currency, status) VALUES
('8a73b8b1-45f4-4c82-9f2d-9e4b2e3a3b1a', 1000.00, CURRENT_TIMESTAMP, '1e8c6265-1c1d-4e89-8d5a-9f776de0842e', 'John Doe', '1234567890', 'SAVINGS', 'USD', 'ACTIVE'),
('8b84c8d1-75f5-4d83-9f2e-9e4c3f3a4b2b', 2000.00, CURRENT_TIMESTAMP, '1e8c6265-1c1d-4e89-8d5a-9f776de0842e', 'John Doe', '2345678901', 'CHECKING', 'EUR', 'ACTIVE'),
('8c95d9e2-85g6-4e94-9f3f-9e5d4f4a5c3c', 3000.00, CURRENT_TIMESTAMP, '1e8c6265-1c1d-4e89-8d5a-9f776de0842e', 'John Doe', '3456789012', 'BUSINESS', 'GBP', 'ACTIVE');

-- Cards for John's accounts
INSERT INTO card (id, expiration_date, account_id, card_holder_name, card_holderuuid, card_number, cvv, status) VALUES
('f34b3d7c-3c39-4c9e-bc75-3d7d3e9f2f1b', '2025-12-31', '8a73b8b1-45f4-4c82-9f2d-9e4b2e3a3b1a', 'John Doe', '1e8c6265-1c1d-4e89-8d5a-9f776de0842e', '1111-2222-3333-4444', '123', 'ACTIVE'),
('g45c4e8d-4d4a-4d94-bc85-4d8d4f4a6d4d', '2026-11-30', '8a73b8b1-45f4-4c82-9f2d-9e4b2e3a3b1a', 'John Doe', '1e8c6265-1c1d-4e89-8d5a-9f776de0842e', '5555-6666-7777-8888', '456', 'ACTIVE');

-- Repeat similar INSERT statements for other accounts and cards
-- Accounts for Jane Smith
INSERT INTO account (id, balance, created_date, users_id, account_holder_name, account_number, account_type, currency, status) VALUES
('9f22b47e-9237-4d4e-b0ff-7d8d7a0f15d7', 2500.00, CURRENT_TIMESTAMP, 'e6f7008c-758b-4a3a-8b7d-7d0d5f638b2f', 'Jane Smith', '4567890123', 'SAVINGS', 'USD', 'ACTIVE'),
('9g33c8d2-83h7-4d95-9f4g-9f5e4f5a6d5e', 3500.00, CURRENT_TIMESTAMP, 'e6f7008c-758b-4a3a-8b7d-7d0d5f638b2f', 'Jane Smith', '5678901234', 'CHECKING', 'EUR', 'ACTIVE'),
('9h44d9e3-94i8-4e96-9f5h-9g6f5g6a7d6f', 4500.00, CURRENT_TIMESTAMP, 'e6f7008c-758b-4a3a-8b7d-7d0d5f638b2f', 'Jane Smith', '6789012345', 'BUSINESS', 'GBP', 'ACTIVE');

-- Cards for Jane's accounts
INSERT INTO card (id, expiration_date, account_id, card_holder_name, card_holderuuid, card_number, cvv, status) VALUES
('g56d4e9d-4d5a-4e95-bd96-5d9e5g5a7e6d', '2025-12-31', '9f22b47e-9237-4d4e-b0ff-7d8d7a0f15d7', 'Jane Smith', 'e6f7008c-758b-4a3a-8b7d-7d0d5f638b2f', '2222-3333-4444-5555', '789', 'ACTIVE'),
('h67e5f0e-5e6b-5f06-ce07-6e7f6h6a8f7e', '2026-11-30', '9f22b47e-9237-4d4e-b0ff-7d8d7a0f15d7', 'Jane Smith', 'e6f7008c-758b-4a3a-8b7d-7d0d5f638b2f', '6666-7777-8888-9999', '012', 'ACTIVE');

-- Accounts for Alice Jones
INSERT INTO account (id, balance, created_date, users_id, account_holder_name, account_number, account_type, currency, status) VALUES
('af33c8d3-94i9-4f07-9g6i-9h7g6h7a8e7g', 1500.00, CURRENT_TIMESTAMP, 'd4b7b4f3-9d8a-4f9e-8e2c-9f776de0843e', 'Alice Jones', '7890123456', 'SAVINGS', 'USD', 'ACTIVE'),
('bg44d9e4-95j0-4g18-9h7j-9i8h7h8a9f8h', 2500.00, CURRENT_TIMESTAMP, 'd4b7b4f3-9d8a-4f9e-8e2c-9f776de0843e', 'Alice Jones', '8901234567', 'CHECKING', 'EUR', 'ACTIVE'),
('ch55e0f5-06k1-4h29-9i8k-9j9i8i9a0g9i', 3500.00, CURRENT_TIMESTAMP, 'd4b7b4f3-9d8a-4f9e-8e2c-9f776de0843e', 'Alice Jones', '9012345678', 'BUSINESS', 'GBP', 'ACTIVE');

-- Cards for Alice's accounts
INSERT INTO card (id, expiration_date, account_id, card_holder_name, card_holderuuid, card_number, cvv, status) VALUES
('h78e5f0f-5e6b-5f06-ce07-6e7f6h6a8f7e', '2025-12-31', 'af33c8d3-94i9-4f07-9g6i-9h7g6h7a8e7g', 'Alice Jones', 'd4b7b4f3-9d8a-4f9e-8e2c-9f776de0843e', '3333-4444-5555-6666', '345', 'ACTIVE'),
('i89f6g0g-6f7c-6g17-df18-7f8h7i7a9g8h', '2026-11-30', 'af33c8d3-94i9-4f07-9g6i-9h7g6h7a8e7g', 'Alice Jones', 'd4b7b4f3-9d8a-4f9e-8e2c-9f776de0843e', '7777-8888-9999-0000', '678', 'ACTIVE');

-- Payments
INSERT INTO payment (id, amount, payment_date, account_id, description, payment_type, status) VALUES
('p1', 200.00, CURRENT_TIMESTAMP, '8a73b8b1-45f4-4c82-9f2d-9e4b2e3a3b1a', 'Transfer to Jane', 'TRANSFER', 'COMPLETED'),
('p2', 150.00, CURRENT_TIMESTAMP, '9f22b47e-9237-4d4e-b0ff-7d8d7a0f15d7', 'Payment to Alice', 'TRANSFER', 'PENDING'),
('p3', 100.00, CURRENT_TIMESTAMP, 'af33c8d3-94i9-4f07-9g6i-9h7g6h7a8e7g', 'Transfer to John', 'TRANSFER', 'COMPLETED');
