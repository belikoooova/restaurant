--liquibase formatted sql
--changeset Maria Belikova:1
create table _dish
(
    id           bigserial
        constraint pk_dish primary key,
    amount       integer,
    cooking_time integer,
    in_menu      boolean,
    price        integer,
    title        varchar(255)
);