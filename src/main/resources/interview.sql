create table products
(
    product_id bigint           not null
        primary key,
    name       varchar(255)     not null,
    price      decimal(10, 2)   not null,
    like_count bigint default 0 null
);

create table users
(
    user_id  bigint       not null
        primary key,
    account  varchar(255) not null,
    password varchar(255) not null,
    salt     tinyblob     null,
    cart_id  bigint       not null,
    constraint account
        unique (account)
);

create table carts
(
    cart_id bigint not null
        primary key,
    user_id bigint not null,
    constraint carts_ibfk_1
        foreign key (user_id) references users (user_id)
);

create table cart_products
(
    cart_product_id bigint         not null
        primary key,
    cart_id         bigint         not null,
    product_id      bigint         not null,
    product_price   decimal(10, 2) not null,
    quantity        int default 0  not null,
    product_name    varchar(255)   not null,
    constraint cart_id
        unique (cart_id, product_id),
    constraint cart_products_ibfk_1
        foreign key (cart_id) references carts (cart_id),
    constraint cart_products_ibfk_2
        foreign key (product_id) references products (product_id)
);

create index product_id
    on cart_products (product_id);

create table like_records
(
    like_record_id bigint       not null
        primary key,
    product_id     bigint       not null,
    user_id        bigint       not null,
    product_name   varchar(255) not null,
    constraint user_id
        unique (user_id, product_id),
    constraint like_records_ibfk_1
        foreign key (product_id) references products (product_id),
    constraint like_records_ibfk_2
        foreign key (user_id) references users (user_id)
);

create index product_id
    on like_records (product_id);

-- 插入商品数据
INSERT INTO products (product_id, name, price, like_count)
VALUES
    (1, '华为 P50 Pro', 5999.00, 800),
    (2, '戴尔 XPS 13 笔记本', 8999.00, 700),
    (3, '小米智能手环 7', 299.00, 1500),
    (4, '苹果 AirPods Pro 2', 1899.00, 1200),
    (5, '索尼 PlayStation 5', 4999.00, 600),
    (6, '美的变频空调 1.5 匹', 3599.00, 500),
    (7, '联想拯救者 Y9000P 游戏本', 9999.00, 900),
    (8, '九阳破壁机', 999.00, 1000),
    (9, '海尔双开门冰箱', 4999.00, 400),
    (10, '佳能 EOS R6 相机', 15999.00, 300);