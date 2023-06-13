create table audiobook_musics
(
    audiobook_id varchar(255) not null
        constraint fknr4mp7pvr3jtdtwttb3olevl1
            references audiobook,
    music_id     varchar(255) not null
        constraint fksg5kiqvwl8q8lvov2t2ibp501
            references music
);

alter table audiobook_musics
    owner to postgres;

