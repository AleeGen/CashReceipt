-----------------------------------------------------------------------------------------------------------------------
-- 1. Вывести к каждому самолету класс обслуживания и количество мест этого класса
select aircraft_code,
       fare_conditions,
       count(*) quantity
from seats
group by aircraft_code, fare_conditions
order by aircraft_code;
-----------------------------------------------------------------------------------------------------------------------
-- 2. Найти 3 самых вместительных самолета (модель + кол-во мест)
select aircraft_code, model ->> 'ru', count(*) quantity
from aircrafts_data
         inner join seats using (aircraft_code)
group by aircraft_code, model
order by quantity desc
limit 3;
-- Из view:
select aircraft_code,
       model,
       count(*) quantity
from aircrafts
         inner join seats using (aircraft_code)
group by aircraft_code, model
order by quantity desc
limit 3;
-----------------------------------------------------------------------------------------------------------------------
-- 3. Вывести код,модель самолета и места не эконом класса для самолета 'Аэробус A321-200' с сортировкой по местам
select aircraft_code, model ->> 'ru', seat_no
from aircrafts_data
         inner join seats using (aircraft_code)
where model @> '{"ru": "Аэробус A321-200"}'
  and fare_conditions != 'Economy'
order by seat_no;
-- Из view:
select aircraft_code, model, seat_no
from aircrafts
         inner join seats using (aircraft_code)
where model = 'Аэробус A321-200'
  and fare_conditions != 'Economy'
order by seat_no;
-----------------------------------------------------------------------------------------------------------------------
-- 4. Вывести города в которых больше 1 аэропорта ( код аэропорта, аэропорт, город)
select airport_code, airport_name ->> 'ru' as airport_name, city ->> 'ru' as city
from airports_data
where city in (select distinct city
               from airports_data
               group by city
               having count(*) > 1);
-- Из view:
select airport_code, airport_name, city
from airports
where city in (select distinct city
               from airports
               group by city
               having count(*) > 1);
-----------------------------------------------------------------------------------------------------------------------
-- 5. Найти ближайший вылетающий рейс из Екатеринбурга в Москву, на который еще не завершилась регистрация
select *
from flights
where departure_airport in (select airport_code from airports_data where city @> '{"ru": "Екатеринбург"}')
  and arrival_airport in (select airport_code from airports_data where city @> '{"ru": "Москва"}')
  and status in ('Scheduled', 'On Time', 'Delayed')
order by scheduled_departure
limit 1;
-- Из view:
select *
from flights_v
where departure_city = 'Екатеринбург'
  and arrival_city = 'Москва'
  and status in ('Scheduled', 'On Time', 'Delayed')
order by scheduled_departure
limit 1;
-----------------------------------------------------------------------------------------------------------------------
-- 6. Вывести самый дешевый и дорогой билет и стоимость ( в одном результирующем ответе)
select string_agg(ticket_no, ',') as list_tickets, max(amount) as min_max_amount
from ticket_flights
where amount = (select max(amount) from ticket_flights)
union
select string_agg(ticket_no, ',') as list_tickets, min(amount)
from ticket_flights
where amount = (select min(amount) from ticket_flights);
-----------------------------------------------------------------------------------------------------------------------
-- 7. Написать DDL таблицы Customers , должны быть поля id , firstName, LastName, email , phone.
-- Добавить ограничения на поля ( constraints) .
create table public.customers
(
    id        bigint      not null unique generated always as identity,
    firstName varchar(30) not null,
    lastName  varchar(30) not null,
    email     varchar(30),
    phone     varchar(15),
    primary key (id)
);
-----------------------------------------------------------------------------------------------------------------------
-- 8. Написать DDL таблицы Orders , должен быть id, customerId,   quantity.
-- Должен быть внешний ключ на таблицу customers + ограничения
create table public.orders
(
    id         bigint not null unique generated always as identity,
    customerId bigint not null,
    quantity   int    not null,
    primary key (id),
    foreign key (customerId) references public.customers (id)
        on update cascade
        on delete no action
);
-----------------------------------------------------------------------------------------------------------------------
-- 9. Написать 5 insert в эти таблицы
insert into public.customers (firstName, lastName, email, phone)
values ('Alexey', 'Leonenko', 'leshaleonenko@mail.ru', '80447290261'),
       ('firstName1', 'lastName1', 'email1@mail.ru', '80291111111'),
       ('firstName2', 'lastName2', 'email2@mail.ru', '80292222222'),
       ('firstName3', 'lastName3', 'email3@mail.ru', '80293333333'),
       ('firstName4', 'lastName4', 'email4@mail.ru', '80294444444');
insert into public.orders (customerId, quantity)
values (1, 11),
       (2, 22),
       (3, 33),
       (4, 44),
       (5, 55);
-----------------------------------------------------------------------------------------------------------------------
-- 10. удалить таблицы
drop table if exists public.orders;
drop table if exists public.customers;
-----------------------------------------------------------------------------------------------------------------------
-- 11. Вывести топ 3 популярных аэропортов за последний год (в конкурсе учавствуют аэропорты, заработавшие более 500 млн)
select departure_airport, count(*) as number_people
from flights
         inner join ticket_flights using (flight_id)
where actual_departure between bookings.now() - interval '1' year and bookings.now()
group by departure_airport
having sum(amount) < 500000000
order by number_people desc
limit 3;