create table if not exists registration_codes (
        id bigserial not null,
        code varchar(255),
        email varchar(255),
        primary key (id)
    );
create table if not exists roles (
            id bigserial not null,
            name varchar(255),
            primary key (id)
        );
create table if not exists user_roles (
                role_id bigint,
                user_id bigint not null,
                primary key (user_id)
            );
create table if not exists users (
                    birthday date,
                    bonus float(53),
                    enabled boolean,
                    id bigserial not null,
                    branch varchar(255),
                    email varchar(255),
                    password varchar(255),
                    username varchar(255),
                    work_schedule varchar(255),
                    primary key (id)
                );
alter table if exists user_roles
                       add constraint user_roles_role_id_fk
                       foreign key (role_id)
                       references roles;

alter table if exists user_roles
      add constraint user_roles_user_id_fk
      foreign key (user_id)
      references users;