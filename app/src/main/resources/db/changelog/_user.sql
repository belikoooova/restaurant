--liquibase formatted sql
--changeset Maria Belikova:1
create table _user
(
    username varchar(255) not null
        constraint pk_user
            primary key,
    password varchar(255),
    role     varchar(255)
        constraint _user_role_check
            check ((role)::text = ANY (ARRAY [('USER'::character varying)::text, ('ADMIN'::character varying)::text])),
    id       bigserial
);