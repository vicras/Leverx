DELETE from game_object;

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

insert into game_object (id, created_at, entity_status, updated_at, approved_status, description, title, owner_id) VALUES
(8, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'DECLINE', 'DECLINE ACTIVE game object 8 owner 3', 'Game object 8', 3);