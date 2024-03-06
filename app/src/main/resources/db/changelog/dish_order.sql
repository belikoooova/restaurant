--liquibase formatted sql
--changeset Maria Belikova:1
create table dish_order
(
    order_id bigint not null
        constraint fk_dish_order_order
            references _order,
    dish_id  bigint not null
        constraint fk_dish_order_dish
            references _dish
);
