create table music_artists
(
    music_music_id    integer      not null,
    artists_artist_id varchar(255) not null
        constraint fk3ee93grl49ksiy4ewb1fmk7x7
            references table_artist
);

alter table music_artists
    owner to postgres;

