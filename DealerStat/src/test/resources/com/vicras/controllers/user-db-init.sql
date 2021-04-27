delete
from User;

insert into User (id, created_at, entity_status, updated_at, email, first_name, last_name, password, role)
values (1, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'admin@gmail.com', 'admin_name', 'admin_surname',
        '$2y$12$KHK0W.L4AnUvRLhEDK0yr.S9CIVwlSyjmz4gJQFwxMDeIPj1rJjIy', 'ADMIN');

insert into User (id, created_at, entity_status, updated_at, email, first_name, last_name, password, role)
values (2, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'trader@gmail.com', 'trader_name', 'trader_surname',
        '$2y$12$RCDO9Td.YUiXMwWg1JwhROjassi2jidYXt5QNiTZUGdAPeQwu2512', 'TRADER');

insert into User (id, created_at, entity_status, updated_at, email, first_name, last_name, password, role)
values (3, '2021-04-16 20:00:00.00', 'INACTIVE', null, 'inactive_trader@gmail.com', 'inactive_trader_name',
        'inactive_trader_surname',
        '$2y$12$RCDO9Td.YUiXMwWg1JwhROjassi2jidYXt5QNiTZUGdAPeQwu2512', 'TRADER');