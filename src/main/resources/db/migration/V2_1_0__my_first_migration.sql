create table IF NOT EXISTS app_user
(
    user_id  serial not null constraint app_user_pkey primary key,
    user_name         varchar(36),
    encryted_password varchar(128),
    enabled           boolean
    );

create table IF NOT EXISTS role
(
    role_id serial not null constraint role_pkey primary key,
    role_name varchar(30)
    );


CREATE TABLE IF NOT EXISTS user_role (
                                         id SERIAL PRIMARY KEY,
                                         app_user_user_id integer,
                                         role_role_id integer,
                                         FOREIGN KEY (app_user_user_id) REFERENCES app_user (user_id)  ON DELETE CASCADE,
    FOREIGN KEY (role_role_id) REFERENCES role (role_id) ON DELETE CASCADE
    );