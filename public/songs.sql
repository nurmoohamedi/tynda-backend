create table songs
(
    id       varchar(255) not null
        primary key,
    date     varchar(255),
    name     varchar(255),
    path     varchar(255),
    size     double precision,
    uploader varchar(255)
);

alter table songs
    owner to postgres;

