--liquibase formatted sql
--changeset Maria Belikova:1
create table _review
(
    id bigserial
        constraint pk_review primary key,
    order_id bigint not null
        constraint fk_review_order
            references _order,
    dish_id  bigint not null
        constraint fk_review_dish
            references _dish,
    score integer,
    comment varchar(255)
);