drop table account

-- Tạo bảng Account cho PostgreSQL
CREATE TABLE account (
                         id SERIAL PRIMARY KEY,
                         account_id VARCHAR(8) UNIQUE NOT NULL,
                         name VARCHAR(100),
                         age INTEGER,
                         email VARCHAR(255)
);


-- Thêm 5 data mẫu
INSERT INTO account (account_id, name, age, email) VALUES
                                                       ('AC000001', 'John Doe', 30, 'john.doe@example.com'),
                                                       ('AC000002', 'Jane Smith', 25, 'jane.smith@example.com'),
                                                       ('AC000003', 'Alice Johnson', 28, 'alice.johnson@example.com'),
                                                       ('AC000004', 'Bob Brown', 35, 'bob.brown@example.com'),
                                                       ('AC000005', 'Charlie Davis', 22, 'charlie.davis@example.com');

select * from account a 