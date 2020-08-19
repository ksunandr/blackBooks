-- auto-generated definition
create table author
(
    id         integer not null
        constraint author_pkey
            primary key,
    name       varchar(255),
    patronymic varchar(255),
    surname    varchar(255)
);

alter table author
    owner to postgres;
-- auto-generated definition
create table book
(
    id               integer not null
        constraint book_pkey
            primary key,
    in_stock         integer,
    name             varchar(255),
    publication_year integer,
    version          bigint  not null
);

alter table book
    owner to postgres;
-- auto-generated definition
create table authors_books
(
    book_id   integer not null
        constraint fkbjp7syqc25hpghr8kfj5me7qk
            references book,
    author_id integer not null
        constraint fko3r5etc5qcjlys9yartx3jgcp
            references author
);

alter table authors_books
    owner to postgres;