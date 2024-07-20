-- Users
INSERT INTO users (id, created_date, email, full_name, password, phone_number, status) VALUES
('1e8c6265-1c1d-4e89-8d5a-9f776de0842e', FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss'), 'john.doe@example.com', 'John Doe', 'password', '1234567890', 'ACTIVE'),
('480f46eb-a046-4ef8-b5fb-c02d96c89264', FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss'), 'jane.smith@example.com', 'Jane Smith', 'password', '0987654321', 'ACTIVE'),
('d4b7b4f3-9d8a-4f9e-8e2c-9f776de0843e', FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss'), 'alice.jones@example.com', 'Alice Jones', 'password', '1122334455', 'ACTIVE');
-- Accounts for John Doe
INSERT INTO account (balance, created_date, id, users_id, account_holder_full_name, account_name, account_type, currency, status) VALUES
(1000.00, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss'), 'c09f380e-08cd-4a6f-a01f-e373950f995f', '1e8c6265-1c1d-4e89-8d5a-9f776de0842e', 'John Doe', 'USA', 'SAVINGS', 'USD', 'ACTIVE'),
(1000.00, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss'), '0d5b8bd3-9dd1-4ac8-b353-00010e64eb55', '1e8c6265-1c1d-4e89-8d5a-9f776de0842e', 'John Doe', 'USA RESERVE', 'SAVINGS', 'USD', 'ACTIVE'),
(2000.00, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss'), '289de4e6-c8b6-4aac-9443-bfa5403453de', '1e8c6265-1c1d-4e89-8d5a-9f776de0842e', 'John Doe', 'EURO Account', 'CHECKING', 'EUR', 'ACTIVE'),
(3000.00, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss'), 'cddc1fd7-fd00-4953-ab81-0147ec5a1efd', '1e8c6265-1c1d-4e89-8d5a-9f776de0842e', 'John Doe', 'MOLDOVA', 'BUSINESS', 'MDL', 'ACTIVE');
-- Cards for John Doe accounts
INSERT INTO card (expiration_date, account_id, id, card_holder_full_name, card_holderuuid, card_number, cvv, status) VALUES
('2015-12-29', 'c09f380e-08cd-4a6f-a01f-e373950f995f', 'd2b6bfcc-c256-4fb0-8558-4b392f790eff', 'John Doe', '1e8c6265-1c1d-4e89-8d5a-9f776de0842e', '1111 2222 3333 4444', '123', 'ACTIVE'),
('2006-09-28', 'c09f380e-08cd-4a6f-a01f-e373950f995f', '98fc6ef6-77b1-4a16-bd70-59676189a216', 'John Doe', '1e8c6265-1c1d-4e89-8d5a-9f776de0842e', '2222 6666 7777 8888', '456', 'ACTIVE'),
('2027-03-01', '0d5b8bd3-9dd1-4ac8-b353-00010e64eb55', '2698d0d5-6231-456e-9013-9b0bd43a53ea', 'John Doe', '1e8c6265-1c1d-4e89-8d5a-9f776de0842e', '3333 2222 3333 4444', '321', 'ACTIVE'),
('2018-11-04', '289de4e6-c8b6-4aac-9443-bfa5403453de', '67ee3fe1-cff4-4859-89f7-9ec20b037474', 'John Doe', '1e8c6265-1c1d-4e89-8d5a-9f776de0842e', '4444 2222 3333 4444', '543', 'ACTIVE'),
('2021-10-09', 'cddc1fd7-fd00-4953-ab81-0147ec5a1efd', 'b36595e7-cbfc-44a9-a37b-95a7f2a67c28', 'John Doe', '1e8c6265-1c1d-4e89-8d5a-9f776de0842e', '5555 2222 3333 4444', '789', 'ACTIVE');
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Accounts for Jane Smith
INSERT INTO account (balance, created_date, id, users_id, account_holder_full_name, account_name, account_type, currency, status) VALUES
(1800.00, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss'), 'b33ecf44-1071-43b1-8c28-67e274cccd60', '480f46eb-a046-4ef8-b5fb-c02d96c89264', 'Jane Smith', 'EUROPE TRIP', 'SAVINGS', 'EUR', 'ACTIVE'),
(-1000.00, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss'), 'ed1734d2-38df-4d6e-9a55-aaa5b88f2996', '480f46eb-a046-4ef8-b5fb-c02d96c89264', 'Jane Smith', 'Parents', 'SAVINGS', 'MDL', 'ACTIVE');
-- Cards for Jane Smith accounts
INSERT INTO card (expiration_date, account_id, id, card_holder_full_name, card_holderuuid, card_number, cvv, status) VALUES
('2025-12-31', 'b33ecf44-1071-43b1-8c28-67e274cccd60', '4bc989a5-e51a-4699-a186-2d3a4b6024a5', 'Jane Smith', '480f46eb-a046-4ef8-b5fb-c02d96c89264', '6666 2222 3333 4444', '421', 'ACTIVE'),
('2026-09-30', 'ed1734d2-38df-4d6e-9a55-aaa5b88f2996', '45a2623d-f577-44ed-a35f-c046b55556c2', 'Jane Smith', '480f46eb-a046-4ef8-b5fb-c02d96c89264', '7777 6666 7777 8888', '123', 'ACTIVE');
-- Accounts for Alice Jones
INSERT INTO account (balance, created_date, id, users_id, account_holder_full_name, account_name, account_type, currency, status) VALUES
(1000.00, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss'), 'e7b9ea34-9bd3-4625-81d4-ac53fa843686', 'd4b7b4f3-9d8a-4f9e-8e2c-9f776de0843e', 'Alice Jones', 'SAVINGS', 'SAVINGS', 'EUR', 'ACTIVE'),
(-8000.00, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss'), '9f205c92-b6ae-438e-a5fd-ce2e815d3f7e', 'd4b7b4f3-9d8a-4f9e-8e2c-9f776de0843e', 'Alice Jones', 'Life', 'SAVINGS', 'MDL', 'ACTIVE');
-- Cards for Alice Jones accounts
INSERT INTO card (expiration_date, account_id, id, card_holder_full_name, card_holderuuid, card_number, cvv, status) VALUES
('2025-12-31', 'e7b9ea34-9bd3-4625-81d4-ac53fa843686', '6e0b7527-d115-4b5d-8615-010774db74f1', 'Alice Jones', 'd4b7b4f3-9d8a-4f9e-8e2c-9f776de0843e', '6666 2222 3333 9999', '421', 'ACTIVE'),
('2026-09-30', '9f205c92-b6ae-438e-a5fd-ce2e815d3f7e', '723bd28a-c991-4dce-895c-7a6253b3e731', 'Alice Jones', 'd4b7b4f3-9d8a-4f9e-8e2c-9f776de0843e', '7777 6666 7777 8888', '123', 'ACTIVE');
-- Payments
INSERT INTO payment (amount, payment_date, from_account_id, id, to_account_id, description, payment_type, status) VALUES
(200.00, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss'), 'c09f380e-08cd-4a6f-a01f-e373950f995f', 'f3371556-c57d-4f04-83e4-575120ce8c1d', 'b33ecf44-1071-43b1-8c28-67e274cccd60', 'Transfer to Jane', 'TRANSFER', 'COMPLETED'),
(150.00, FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss'), 'b33ecf44-1071-43b1-8c28-67e274cccd60', '5e37c6fa-7b7d-432f-b466-732498b37f6f', 'e7b9ea34-9bd3-4625-81d4-ac53fa843686', 'Payment to Alice', 'TRANSFER', 'PENDING'),
(100.00, '2011-08-21 02:01:22', 'e7b9ea34-9bd3-4625-81d4-ac53fa843686', 'f3a3c171-512a-4550-b933-89d24d59597f', 'cddc1fd7-fd00-4953-ab81-0147ec5a1efd', 'Transfer to John', 'TRANSFER', 'COMPLETED'),
(100.00, '2012-08-21 01:01:22', 'e7b9ea34-9bd3-4625-81d4-ac53fa843686', '85753405-5f73-461f-b049-f62b7423102d', 'cddc1fd7-fd00-4953-ab81-0147ec5a1efd', 'Transfer to John', 'TRANSFER', 'COMPLETED'),
(100.00, '2013-09-21 02:01:22', 'e7b9ea34-9bd3-4625-81d4-ac53fa843686', '3f2592b4-d5d5-4d61-ba00-f16eb80d2998', 'cddc1fd7-fd00-4953-ab81-0147ec5a1efd', 'Transfer to John', 'TRANSFER', 'COMPLETED');