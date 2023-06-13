create table music
(
    music_id    varchar(255) not null
        primary key,
    duration    bigint,
    img_link    varchar(255),
    local_path  varchar(255),
    name        varchar(255),
    public_path varchar(255)
);

alter table music
    owner to postgres;

