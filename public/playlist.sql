create table playlist
(
    playlist_id integer not null
        primary key,
    img_link    varchar(255),
    name        varchar(255)
);

alter table playlist
    owner to postgres;

