create table roles
(
    id   serial
        primary key,
    name varchar(20)
);

alter table roles
    owner to postgres;

