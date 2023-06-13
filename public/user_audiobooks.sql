create table user_audiobooks
(
    user_id      bigint       not null
        constraint fkh7pex518q2fusdd0xrl6vws7a
            references users,
    audiobook_id varchar(255) not null
        constraint uk_3qbwjptk4wygka9jpokku5wnt
            unique
        constraint fkfcdnerkd8rbplx8bh5e84of5l
            references audiobook
);

alter table user_audiobooks
    owner to postgres;

