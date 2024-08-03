INSERT INTO customers (name, email, age)
VALUES ('Eugene', 'Eugene@gmail.com', 35),
       ('Tatiyana', 'tatiyana@gmail.com', 34),
       ('Nikita', 'nikita@gmail.com', 35);

INSERT INTO accounts (number, currency, balance, customer_id)
VALUES ('0123456789', 'USD', 100.0, 1),
       ('1234567890', 'USD', 50.0, 2);

INSERT INTO employers (name, address)
VALUES ('Facebook', 'California, USA'),
       ('LinkedIn', 'California, USA'),
       ('Apple', 'California, USA');

INSERT INTO customers_employers (customer_id, employer_id)
VALUES (1, 1),
       (2, 2),
       (3, 1);