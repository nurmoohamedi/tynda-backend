create table artist_musics
(
    artist_id varchar(255) not null
        constraint fk7p86mcsjyignfx1hauo1ewb2l
            references table_artist,
    music_id  varchar      not null
        constraint fk74qsiidah9ense9mrdb1e8xem
            references music
);

alter table artist_musics
    owner to postgres;

