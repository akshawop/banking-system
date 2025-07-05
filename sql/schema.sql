CREATE DATABASE IF NOT EXISTS banking_system;

USE banking_system;

CREATE TABLE bank (
    bank_id TINYINT PRIMARY KEY DEFAULT 1 CHECK (bank_id = 1),
    bank_code CHAR(4) NOT NULL,
    bank_name VARCHAR(100) NOT NULL,
    creation_date DATETIME NOT NULL DEFAULT (NOW()),
    CONSTRAINT ck_bank_code_length CHECK (LENGTH(bank_code) = 4)
);

CREATE TABLE branch (
    branch_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    branch_code CHAR(6) NOT NULL,
    branch_name VARCHAR(50) NOT NULL,
    street VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    district VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    pin_code CHAR(6) NOT NULL,
    opening_date DATE NOT NULL DEFAULT (CURRENT_DATE),
    CONSTRAINT uk_branch_code UNIQUE (branch_code),
    CONSTRAINT ck_branch_code_length CHECK (LENGTH(branch_code) = 6),
    CONSTRAINT unique_branch_name UNIQUE (branch_name)
);

CREATE TABLE customer (
    customer_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(25) NOT NULL,
    mid_name VARCHAR(25),
    last_name VARCHAR(25) NOT NULL,
    aadhaar CHAR(12) NOT NULL,
    pan CHAR(10),
    street VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    district VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    pin_code CHAR(6) NOT NULL,
    phone CHAR(10) NOT NULL,
    email VARCHAR(75),
    registration_date DATE NOT NULL DEFAULT (CURRENT_DATE),
    CONSTRAINT unique_aadhaar UNIQUE (aadhaar),
    CONSTRAINT ck_customer_aadhaar_length CHECK (LENGTH(aadhaar) = 12),
    CONSTRAINT ck_customer_aadhaar_chars CHECK (aadhaar REGEXP '^[0-9]+$'),
    CONSTRAINT ck_customer_pan_length CHECK (LENGTH(pan) = 10),
    CONSTRAINT ck_customer_phone_length CHECK (LENGTH(phone) = 10),
    CONSTRAINT ck_customer_phone_chars CHECK (phone REGEXP '^[0-9]+$')
);

CREATE TABLE account (
    account_number INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    branch INT UNSIGNED NOT NULL,
    account_type ENUM('SAVINGS', 'CURRENT') NOT NULL,
    customer INT UNSIGNED NOT NULL,
    nominee INT UNSIGNED,
    balance DECIMAL(14, 2) NOT NULL DEFAULT 0.00,
    min_balance DECIMAL(7, 2) UNSIGNED NOT NULL DEFAULT 0.00,
    account_status ENUM('ACTIVE', 'BLOCKED') NOT NULL,
    opening_date DATE NOT NULL DEFAULT (CURRENT_DATE),
    FOREIGN KEY (branch) REFERENCES branch(branch_id),
    FOREIGN KEY (customer) REFERENCES customer(customer_id),
    FOREIGN KEY (nominee) REFERENCES customer(customer_id),
    CONSTRAINT uk_account_type_customer UNIQUE (account_type, customer)
);

CREATE TABLE account_transaction (
    transaction_id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    account INT UNSIGNED NOT NULL,
    description TEXT NOT NULL,
    transaction_type ENUM('CREDIT', 'DEBIT') NOT NULL,
    mode ENUM(
        'ATM',
        'CHEQUE',
        'BRANCH',
        'UPI',
        'NETBANKING',
        'OTHER'
    ) NOT NULL,
    amount DECIMAL(14, 2) NOT NULL,
    balance DECIMAL(14, 2) NOT NULL,
    transaction_timestamp DATETIME NOT NULL DEFAULT (NOW()),
    FOREIGN KEY (account) REFERENCES account(account_number)
);

CREATE TABLE card (
    card_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    card_number CHAR(16) NOT NULL,
    cvv CHAR(3) NOT NULL,
    card_type ENUM('DEBIT', 'CREDIT') NOT NULL,
    account INT UNSIGNED NOT NULL,
    issued_date DATE NOT NULL DEFAULT (CURRENT_DATE),
    expiry_date DATE NOT NULL,
    card_pin CHAR(4) NOT NULL,
    card_status ENUM('ACTIVE', 'BLOCKED', 'EXPIRED') NOT NULL,
    FOREIGN KEY (account) REFERENCES account(account_number),
    CONSTRAINT uk_card_number UNIQUE (card_number),
    CONSTRAINT ck_card_number_chars CHECK (card_number REGEXP '^[0-9]+$'),
    CONSTRAINT ck_cvv_chars CHECK (cvv REGEXP '^[0-9]+$'),
    CONSTRAINT ck_expiry_date CHECK (issued_date < expiry_date),
    CONSTRAINT ck_card_pin_chars CHECK (card_pin REGEXP '^[0-9]+$')
);