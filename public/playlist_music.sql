create table playlist_music
(
    playlist_id integer not null
        constraint fkq9o07ljjk03aeeqt0q9lwhndk
            references playlist,
    music_id    varchar not null
        constraint fk5g0xtl5e89uycye0jo1ll65sq
            references music
);

alter table playlist_music
    owner to postgres;

