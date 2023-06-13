create table users
(
    id         bigserial
        primary key,
    email      varchar(50)
        constraint uk6dotkott2kjsp8vw4d0m25fb7
            unique,
    password   varchar(120),
    username   varchar(20)
        constraint ukr43af9ap4edm43mmtq01oddj6
            unique,
    img_link   varchar(255),
    subscribed boolean
);

alter table users
    owner to postgres;

