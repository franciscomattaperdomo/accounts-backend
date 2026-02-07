CREATE TABLE IF NOT EXISTS `customers` (
  `customer_id` int AUTO_INCREMENT  PRIMARY KEY,
  `document_type` varchar(20) NOT NULL,
  `document_number` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `created_at` date NOT NULL,
  `created_by` varchar(20) NOT NULL,
  `updated_at` date DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `accounts` (
  `account_id` int AUTO_INCREMENT  PRIMARY KEY,
  `customer_id` int NOT NULL,
  `account_number` int AUTO_INCREMENT  NOT NULL,
  `account_type` varchar(100) NOT NULL,
  `status` varchar(20) NOT NULL,
  `created_at` date NOT NULL,
  `created_by` varchar(20) NOT NULL,
  `updated_at` date DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL
);