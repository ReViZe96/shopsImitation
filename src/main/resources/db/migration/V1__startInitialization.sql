create table users  (id bigserial, name varchar(500), passport_seria integer, passport_number integer, age integer, primary key (id));

create table items (id bigserial, name varchar(500), description text, price integer, primary key (id));

insert into users (name, passport_seria, passport_number, age)
values
('Касьянов Артём Александрович', 6010, 874560, 26),
('Казанцев Михаил Юрьевич', 6012, 847569, 24),
('Демидов Александр Владимирович', 6008, 348764, 27),
('Захарко Дарья Игоревна', 6008, 037596, 28),
('Букатов Вячеслав Валерьевич', 6011, 301858, 25),
('Корпорат Айрат Одинэсович', 6666, 666666, 50);

insert into items (name, description, price)
values
('Кроссовки', 'Реплика известнейшего бренда "Abibas" под названием - "Aрibas"', 5000),
('Джинсы', 'Разорванные в хлам в районе филейной части, согласно последним модным трендам', 4500),
('Рубашка', 'Без рукавов, следовательно - поло', 3000),
('Деловой костюм', 'Универсальный: корпоративы, свадьбы, похороны', 60000),
('Свитер', '100% шерсть шерстистого мамонта', 7000);

create table users_items (user_id int8 references users(id), item_id int8 references items(id));

insert into users_items (user_id, item_id) values
(6, 2), (6, 4), (6, 1), (6, 5), (6, 3), (5, 1), (5, 3), (4, 1), (4, 5), (3, 4), (3, 5), (2, 5),
(2, 3), (1, 1), (1, 4);