delete from game_object_game;
delete from game_object;
delete from Game;
delete from Comment;
delete from User;

/**********************************************************************************************************************/
/* User section*/
/*password admin*/
insert into User (id, created_at, entity_status, updated_at, email, first_name, last_name, password, role)
values (1, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'admin@gmail.com', 'admin_name', 'admin_surname',
        '$2y$12$KHK0W.L4AnUvRLhEDK0yr.S9CIVwlSyjmz4gJQFwxMDeIPj1rJjIy', 'ADMIN');

/*password trader*/
insert into User (id, created_at, entity_status, updated_at, email, first_name, last_name, password, role)
values (2, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'trader@gmail.com', 'trader_name', 'trader_surname',
        '$2y$12$RCDO9Td.YUiXMwWg1JwhROjassi2jidYXt5QNiTZUGdAPeQwu2512', 'TRADER');

/*password trader*/
insert into User (id, created_at, entity_status, updated_at, email, first_name, last_name, password, role)
values (3, '2021-04-16 20:00:00.00', 'INACTIVE', null, 'inactive_trader@gmail.com', 'inactive_trader_name',
        'inactive_trader_surname',
        '$2y$12$RCDO9Td.YUiXMwWg1JwhROjassi2jidYXt5QNiTZUGdAPeQwu2512', 'TRADER');


/**********************************************************************************************************************/
/*game object section*/
/*sent*/
insert into game_object (id, created_at, entity_status, updated_at, approved_status, description, title, owner_id) VALUES
(1, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'SENT', 'SENT game object 1 owner 2', 'Game object 1', 2);

insert into game_object (id, created_at, entity_status, updated_at, approved_status, description, title, owner_id) VALUES
(2, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'SENT', 'SENT game object 2 owner 2', 'Game object 2', 2);

/*approved*/
insert into game_object (id, created_at, entity_status, updated_at, approved_status, description, title, owner_id) VALUES
(3, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'APPROVED', 'APPROVED game object 3 owner 2', 'Game object 3', 2);

insert into game_object (id, created_at, entity_status, updated_at, approved_status, description, title, owner_id) VALUES
(4, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'APPROVED', 'APPROVED game object 4 owner 2', 'Game object 4', 2);

/*# deleted*/
insert into game_object (id, created_at, entity_status, updated_at, approved_status, description, title, owner_id) VALUES
(5, '2021-04-16 20:00:00.00', 'DELETED', null, 'APPROVED', 'APPROVED DELETED game object 5 owner 2', 'Game object 5', 2);

insert into game_object (id, created_at, entity_status, updated_at, approved_status, description, title, owner_id) VALUES
(6, '2021-04-16 20:00:00.00', 'DELETED', null, 'SENT', 'SENT DELETED game object 6 owner 2', 'Game object 6', 2);

/*decline*/
insert into game_object (id, created_at, entity_status, updated_at, approved_status, description, title, owner_id) VALUES
(7, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'DECLINE', 'DECLINE ACTIVE game object 7 owner 2', 'Game object 7', 2);

/*for inactive user*/
insert into game_object (id, created_at, entity_status, updated_at, approved_status, description, title, owner_id) VALUES
(8, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'DECLINE', 'DECLINE ACTIVE game object 8 owner 3 (INACTIVE)', 'Game object 8', 3);


/**********************************************************************************************************************/
/*games section*/
insert into Game (id, title, created_at, updated_at, entity_status) values
(1, 'CALL OF DUTY', '2021-04-16 20:00:00.00', null, 'ACTIVE');

insert into Game (id, title, created_at, updated_at, entity_status) values
(2, 'METRO', '2021-04-16 20:00:00.00', null, 'ACTIVE');


/**********************************************************************************************************************/
/*games with game object links section*/
insert into game_object_game (game_object_id, game_id) VALUES (1,1);
insert into game_object_game (game_object_id, game_id) VALUES (1,2);

insert into game_object_game (game_object_id, game_id) VALUES (2,1);

insert into game_object_game (game_object_id, game_id) VALUES (3,1);
insert into game_object_game (game_object_id, game_id) VALUES (3,2);

insert into game_object_game (game_object_id, game_id) VALUES (4,1);

insert into game_object_game (game_object_id, game_id) VALUES (5,1);

insert into game_object_game (game_object_id, game_id) VALUES (6,1);

insert into game_object_game (game_object_id, game_id) VALUES (7,1);
insert into game_object_game (game_object_id, game_id) VALUES (7,2);

/**********************************************************************************************************************/
/*comments section*/
/*sent*/
insert into Comment (id, created_at, entity_status, updated_at, approved_status, mark, message, user_id) values
(1, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'SENT', 4, 'message 1 SENT with mark 4 for user 2', 2);

insert into Comment (id, created_at, entity_status, updated_at, approved_status, mark, message, user_id) values
(2, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'SENT', 3, 'message 2 SENT with mark 3 for user 2', 2);

/*approved*/
insert into Comment (id, created_at, entity_status, updated_at, approved_status, mark, message, user_id) values
(3, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'APPROVED', 3, 'message 3 APPROVED with mark 3 for user 2', 2);

insert into Comment (id, created_at, entity_status, updated_at, approved_status, mark, message, user_id) values
(4, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'APPROVED', 5, 'message 4 APPROVED with mark 5 for user 2', 2);

/*decline*/
insert into Comment (id, created_at, entity_status, updated_at, approved_status, mark, message, user_id) values
(5, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'DECLINE', 5, 'message 5 DECLINE with mark 5 for user 2', 2);

/*user 3*/
insert into Comment (id, created_at, entity_status, updated_at, approved_status, mark, message, user_id) values
(6, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'DECLINE', 5, 'message 6 DECLINE with mark 5 for user 3(INACTIVE)', 3);