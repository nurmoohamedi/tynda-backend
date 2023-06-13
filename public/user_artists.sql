create table user_artists
(
    user_id   bigint       not null
        constraint fkbnvif8he1jppsb3qvxpfpr08c
            references users,
    artist_id varchar(255) not null
        constraint uk_j267icwowihf959lu7gbnmm2n
            unique
        constraint fk156ro4d4g4806xr519rk5es18
            references table_artist
);

alter table user_artists
    owner to postgres;

