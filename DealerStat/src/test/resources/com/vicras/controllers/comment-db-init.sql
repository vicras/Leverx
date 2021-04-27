delete from Comment;

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
(6, '2021-04-16 20:00:00.00', 'ACTIVE', null, 'DECLINE', 5, 'message 6 DECLINE with mark 5 for user 3', 3);