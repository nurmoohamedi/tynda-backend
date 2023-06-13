create table audiobook
(
    audiobook_id varchar(255) not null
        primary key,
    author       varchar(255),
    description  varchar(1000),
    dictor       varchar(255),
    img_link     varchar(255),
    name         varchar(255)
);

alter table audiobook
    owner to postgres;

