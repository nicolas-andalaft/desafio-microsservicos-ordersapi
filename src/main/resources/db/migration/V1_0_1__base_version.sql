create table users(
    id bigserial primary key,
    username text not null,
    password text not null,
    dollar_balance numeric,
    enabled boolean not null default true,
    created_on timestamp not null default current_timestamp,
    updated_on timestamp not null default current_timestamp
);

create table user_stock_balances(
    id_user bigint references users(id),
    id_stock bigint not null,
    stock_symbol text not null,
    stock_name text not null,
    volume bigint not null default 0,
    created_on timestamp not null default current_timestamp,
    updated_on timestamp not null default current_timestamp,
    primary key (id_user, id_stock)
);

create table user_orders(
    id bigserial primary key,
    id_user bigint references users(id),
    id_stock bigint not null,
    stock_symbol text not null,
    stock_name text not null,
    volume bigint not null default 0,
    price numeric not null default 0.0,
    type int not null,
    status int not null,
    created_on timestamp not null default current_timestamp,
    updated_on timestamp not null default current_timestamp
);

create table orders_history (
	id bigserial primary key,
	id_order bigint references user_orders(id),
	id_match_order bigint references user_orders(id),
	match_volume bigint not null,
	match_price numeric not null,
	order_type int not null,
	created_on timestamp null default current_timestamp
);

create or replace function set_updated_on()
returns trigger as $$
BEGIN
	new.updated_on = now();
	return new;
END;
$$ language plpgsql;

create trigger users_updated_on
before update on users
for each row 
execute procedure set_updated_on();

create trigger user_orders_updated_on
before update on user_orders
for each row 
execute procedure set_updated_on();

create trigger user_stock_balances_updated_on
before update on user_stock_balances
for each row 
execute procedure set_updated_on();