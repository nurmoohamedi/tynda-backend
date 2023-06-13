create table table_image
(
    id          varchar(255) not null
        primary key,
    image_path  varchar(255),
    name        varchar(255),
    type        varchar(255),
    public_path varchar(255)
);

alter table table_image
    owner to postgres;

