
CREATE TABLE IF NOT EXISTS  branches (
    id BIGSERIAL PRIMARY KEY NOT NULL UNIQUE,
    name VARCHAR(255),
    image_id BIGINT UNIQUE,
    address VARCHAR(255),
    gis_url VARCHAR(255),
    phone_number VARCHAR(20),
    table_count INTEGER NOT NULL,
    work_schedule_id BIGINT UNIQUE
);
CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS coffee_compositions (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    ingredient_id BIGINT,
    warehouse_id BIGINT,
    quantity FLOAT(53),
    product_id BIGINT );

 CREATE TABLE IF NOT EXISTS employee_work_schedule (
        employee_id BIGINT NOT NULL,
        work_schedule_id BIGINT NOT NULL

    );
CREATE TABLE IF NOT EXISTS images (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    public_id VARCHAR(255),
    url VARCHAR(255),
    product_id BIGINT UNIQUE,
    branch_id BIGINT UNIQUE );

CREATE TABLE IF NOT EXISTS ingredients (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255),
    ingredient_type VARCHAR(255) check (ingredient_type in ('RAW_MATERIAL','FINISHED_PRODUCT')),
    quantity FLOAT(53),
    is_running_out BOOLEAN DEFAULT FALSE,
    unit_of_measure VARCHAR(255),
    warehouse_id BIGINT
    );

CREATE TABLE IF NOT EXISTS order_details (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    order_id BIGINT,
    product_id BIGINT,
    quantity INTEGER,
    total_price FLOAT(53)
);

CREATE TABLE IF NOT EXISTS orders (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id BIGINT,
    branch_id BIGINT,
    order_type VARCHAR(255),
    order_status smallint check (order_status between 0 and 4),
    order_date TIMESTAMP(6)
);

CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    category_id BIGINT,
    name VARCHAR(255),
    description VARCHAR(555),
    price FLOAT(53),
    image_id BIGINT UNIQUE,
    branch_id BIGINT );
CREATE TABLE IF NOT EXISTS registration_codes (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    code VARCHAR(255),
    email VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS tables (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    branch_id BIGINT,
    user_id BIGINT,
    table_status VARCHAR(255) check (table_status in ('BUSY','FREE'))
);
CREATE TABLE IF NOT EXISTS user_roles (
        role_id BIGINT,
        user_id BIGINT NOT NULL,
        primary key (user_id)
    );
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(255),
    password VARCHAR(255),
    firstname VARCHAR(255),
    email VARCHAR(255),
    bonus FLOAT(53),
    birthday DATE,
    branch_id BIGINT,
    enabled BOOLEAN
);
CREATE TABLE IF NOT EXISTS warehouse (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    item_name VARCHAR(255),
    unit_of_measure VARCHAR(255),
    quantity INTEGER,
    ingredient_type VARCHAR(255) check (ingredient_type in ('RAW_MATERIAL','FINISHED_PRODUCT')),
    min_limit INTEGER,
    arrival_date TIMESTAMP(6),
    branch_id BIGINT UNIQUE
);
CREATE TABLE IF NOT EXISTS work_schedules (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    branch_id BIGINT UNIQUE,
    day_of_week VARCHAR(255) array,
    start_time TIME(6),
    end_time TIME(6)
);

alter table if exists branches
       add constraint fk_branch_image
       foreign key (image_id)
       references images(id);

   alter table if exists coffee_compositions
       add constraint fk_coffee_composition_ingredient
       foreign key (ingredient_id)
       references ingredients(id);
    alter table if exists coffee_compositions
       add constraint fk_coffee_product
       foreign key (product_id)
       references products(id);
    alter table if exists coffee_compositions
       add constraint fk_warehouse_coffee_composition
       foreign key (warehouse_id)
       references warehouse(id);

ALTER TABLE IF EXISTS employee_work_schedule
ADD CONSTRAINT fk_employee_user_id
FOREIGN KEY (employee_id)
REFERENCES users(id);
ALTER TABLE IF EXISTS employee_work_schedule
ADD CONSTRAINT fk_employee_work_schedule
FOREIGN KEY (work_schedule_id)
REFERENCES work_schedules(id);

ALTER TABLE IF EXISTS images
ADD CONSTRAINT fk_images_branch
FOREIGN KEY (branch_id)
REFERENCES branches(id);
alter table if exists images
       add constraint fk_product_image
       foreign key (product_id)
       references products(id);

ALTER TABLE IF EXISTS ingredients
ADD CONSTRAINT fk_ingredients_warehouse
FOREIGN KEY (warehouse_id)
REFERENCES warehouse(id);

ALTER TABLE IF EXISTS order_details
    ADD CONSTRAINT fk_order_details_order
    FOREIGN KEY (order_id)
    REFERENCES orders (id);
alter table if exists order_details
       add constraint fk_product_order_details
       foreign key (product_id)
       references products(id);

alter table if exists orders
       add constraint fk_branch_orders
       foreign key (branch_id)
       references branches(id);
alter table if exists orders
       add constraint fk_user_orders
       foreign key (user_id)
       references users(id);

alter table if exists products
       add constraint fk_branch_products
       foreign key (branch_id)
       references branches(id);
alter table if exists products
       add constraint fk_product_category
       foreign key (category_id)
       references categories(id);
alter table if exists products
       add constraint fk_product_image
       foreign key (image_id)
       references images(id);

alter table if exists tables
       add constraint  fk_branch_tables
       foreign key (branch_id)
       references branches(id);

  alter table if exists tables
       add constraint fk_user_tables
       foreign key (user_id)
       references users(id);

alter table if exists user_roles
                       add constraint user_roles_role_id_fk
                       foreign key (role_id)
                       references roles(id);
alter table if exists user_roles
                      add constraint user_roles_user_id_fk
                      foreign key (user_id)
                      references users(id);

 alter table if exists users
       add constraint fk_branch_employees
       foreign key (branch_id)
       references branches(id);

alter table if exists warehouse
       add constraint fk_branch_warehouse
       foreign key (branch_id)
       references branches(id);

alter table if exists work_schedules
       add constraint fk_branch_work_schedule
       foreign key (branch_id)
       references branches(id);
