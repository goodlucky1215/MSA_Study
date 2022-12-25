// 회원 테이블 /////////////////////////////////
create table member(
    pk_id integer primary key generated by default as identity,
    id varchar(40) unique,
    email varchar(60) unique,
    nickname varchar(20) not null,
    password_encrypt varchar(300) not null,
    birth date,
    grade varchar(10) not null default 'welcome',
    join_date date
);

#ALTER TABLE MEMBER ADD COLUMN password_encrypt varchar(300) NOT NULL;
#ALTER TABLE MEMBER DROP COLUMN PASSWORD;
commit;

// 주문 테이블 /////////////////////////////////
create table order(
    order_id integer primary key generated by default as identity,
    pk_id integer not null,
    status varchar(20) not null,
    order_date date,
    CONSTRAINT fk_pk_id FOREIGN KEY(pk_id) REFERENCES "member"(pk_id) ON DELETE CASCADE ON UPDATE CASCADE
);

commit;

// 카테고리 테이블 /////////////////////////////////
create table category(
    category_id integer primary key generated by default as identity,
    name varchar(30) not null,
);

commit;

// 판매자 테이블 /////////////////////////////////
create table seller(
    seller_id integer primary key generated by default as identity,
    id varchar(40) unique,
    nickname varchar(20) not null,
    password_encrypt varchar(300) not null,
    join_date date
);

commit;

// 상품 테이블 /////////////////////////////////
create table item(
    item_id integer primary key generated by default as identity,
    seller_id integer not null,
    category_id integer not null,
    price integer not null,
    quantity integer not null,
    CONSTRAINT fk_seller_id FOREIGN KEY(seller_id) REFERENCES "seller"(seller_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_category_id FOREIGN KEY(category_id) REFERENCES "category"(category_id) ON DELETE CASCADE ON UPDATE CASCADE
);

commit;


// 주문 상품 테이블 ////////////////////////////
create table orderitem(
    orderitem_id integer primary key generated by default as identity,
    order_id integer not null,
    item_id integer not null,
    CONSTRAINT fk_order_id FOREIGN KEY(order_id) REFERENCES "order"(order_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_item_id FOREIGN KEY(item_id) REFERENCES "item"(item_id) ON DELETE CASCADE ON UPDATE CASCADE
);

commit;