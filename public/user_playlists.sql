create table user_playlists
(
    user_id     bigint  not null
        constraint fkgmc18weeykvd1b9dqkxpthpx1
            references users,
    playlist_id integer not null
        constraint uk_903316adccsjp3l027u1y4rct
            unique
        constraint fkm7nee1dm1nah5t5ewkupmibf4
            references playlist,
    primary key (user_id, playlist_id)
);

alter table user_playlists
    owner to postgres;

