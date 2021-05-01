create table Game
(
    id bigint not null
        primary key,
    created_at datetime(6) null,
    entity_status varchar(255) null,
    updated_at datetime(6) null,
    title varchar(255) not null
);

create table User
(
    id bigint not null
        primary key,
    created_at datetime(6) null,
    entity_status varchar(255) null,
    updated_at datetime(6) null,
    email varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    password varchar(255) not null,
    role varchar(255) not null,
    constraint UK_e6gkqunxajvyxl5uctpl2vl2p
        unique (email)
);

create table Comment
(
    id bigint not null
        primary key,
    created_at datetime(6) null,
    entity_status varchar(255) null,
    updated_at datetime(6) null,
    approved_status varchar(255) null,
    mark int not null,
    message varchar(255) not null,
    user_id bigint not null,
    constraint FKhlbnnmiua9xpvfq8y1u1a15ie
        foreign key (user_id) references User (id)
);

create table game_object
(
    id bigint not null
        primary key,
    created_at datetime(6) null,
    entity_status varchar(255) null,
    updated_at datetime(6) null,
    approved_status varchar(255) null,
    description varchar(255) not null,
    title varchar(255) not null,
    owner_id bigint not null,
    constraint FK29imhjtvcv0ivr5405hll6ny1
        foreign key (owner_id) references User (id)
);

create table game_object_game
(
    game_object_id bigint not null,
    game_id bigint not null,
    primary key (game_object_id, game_id),
    constraint FKjc2p6mxpxb1jyi2tcokrq98q3
        foreign key (game_id) references Game (id),
    constraint FKr55kvy6itulrbb7ig2lya6owa
        foreign key (game_object_id) references game_object (id)
);

create table hibernate_sequence
(
    next_val bigint null
);

