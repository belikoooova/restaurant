--liquibase formatted sql
--changeset Maria Belikova:1
create table _order
(
    id      bigserial
        constraint pk_order primary key,
    date    date,
    is_paid boolean,
    user_id bigint,
    ready_dishes_amount integer,
    status     varchar(255)
        constraint _order_status_check
            check ((status)::text = ANY (ARRAY [('IN_PROGRESS'::character varying)::text, ('READY'::character varying)::text, ('CANCELLED'::character varying)::text]))
);

--changeset Maria Belikova:2
alter table _order alter column status type smallint using status::smallint;