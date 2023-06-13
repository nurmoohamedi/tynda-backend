create table table_artist
(
    artist_id varchar(255) not null
        primary key,
    date      varchar(255),
    followers integer,
    img_link  varchar(255),
    listeners integer,
    name      varchar(255)
);

alter table table_artist
    owner to postgres;

