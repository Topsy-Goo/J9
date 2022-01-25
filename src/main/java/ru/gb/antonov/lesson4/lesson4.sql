-- Детали реализации.

-- 0. таб.seances
-- -----------------------------------------------------------
-- таб.seances
-- 	  id, holiday, time, price   << таблица не меняется (11 строк для буднего дня и 11 для выходного; выходные отличаются от будней ценой билета)
--
-- таб.films
-- 	  id, title, duration		 << можно добавить фильм
--
-- таб.schedule
-- 	  id, date, id_seance, id_film	  << каждому дню соотв. 11 строк (для выходного или для буднего дня)
--
-- таб.tickets
-- 	  id, id_schedule, row, column	  << список всех проданных билетов
-- -----------------------------------------------------------

-- 1. Цена билета не зависит от названия фильма и, как следствие, от его продолжительности.
-- 2. Билет продаётся не на сеанс (т.seances), а на просмотр. Просмотр — это строка в расписании (т.schedule).
-- 3. В билете указывается номер билета и номер просмотра.
-- 4. Цена является свойством сеанса и формируется по схеме: базовая цена base = 200 в будни или 300 в выходные.
--	  Наценка за время суток добавляется к базовой цене:
--	  9…15 — base + 0%,
--    15…18 — base +10%,
--    18…21 — base +20%,
--    21…24 — base +30%.
-- 5. Везде, где в условии сказано, что нужно посчитать количесво посетителей,
--    в решении вместо посетителей подсчитываются проданные билеты.

CREATE DATABASE IF NOT EXISTS `j9-4` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `j9-4`;

CREATE TABLE `films` (
  `id`       bigint       NOT NULL AUTO_INCREMENT,
  `title`    varchar(256) NOT NULL,
  `duration` time         NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `title_UNIQUE` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `j9-4`.`films` (`id`,`title`,`duration`) VALUES
('1', '«Сказка 1»',    '00:45:00'
 '2', '«Сказка 2»',    '01:30:00'
 '3', '«Матрица 0»',   '02:00:00'
 '4', '«Матрица 1»',   '02:00:00'
 '5', '«Матрица 10»',  '02:00:00'
 '6', '«Матрица 11»',  '01:30:00'
 '7', '«Матрица 100»', '01:00:00'
 '8', '«Матрица 101»', '01:30:00'
 '9', '«Сказка 3»',    '01:00:00');

CREATE TABLE `seances` (
  `id`      bigint       NOT NULL AUTO_INCREMENT,
  `holiday` tinyint      NOT NULL COMMENT 'будний день или выходной',
  `time`    time         NOT NULL COMMENT 'время начала сеанса',
  `price`   decimal(6,2) NOT NULL COMMENT 'Цена просмотра формируется по схеме: базовая цена = 200 в будни или 300 в выходные. Доп. наценка за время суток: 9…15 — 0%, 15…18 —  10%, 18…21 —  20%, 21…24 —  30%.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Поскольку цена билета не зависит от названия фильма, то она зависит от сеаса. Цена билета в ыходные дороже. Также цена зависит от времени суток.';

INSERT INTO `j9-4`.`seances` (`id`,`holiday`,`time`,`price`) VALUES
( '1', '1', '09:00:00', '300.00'
  '2', '1', '10:30:00', '300.00'
  '3', '1', '12:00:00', '300.00'
  '4', '1', '13:30:00', '300.00'
  '5', '1', '15:00:00', '330.00'
  '6', '1', '16:30:00', '330.00'
  '7', '1', '18:00:00', '360.00'
  '8', '1', '19:30:00', '360.00'
  '9', '1', '21:00:00', '390.00'
 '10', '1', '22:30:00', '390.00'
 '11', '1', '24:00:00', '390.00'

 '12', '0', '09:00:00', '200.00'
 '13', '0', '10:30:00', '200.00'
 '14', '0', '12:00:00', '200.00'
 '15', '0', '13:30:00', '200.00'
 '16', '0', '15:00:00', '220.00'
 '17', '0', '16:30:00', '220.00'
 '18', '0', '18:00:00', '240.00'
 '19', '0', '19:30:00', '240.00'
 '20', '0', '21:00:00', '260.00'
 '21', '0', '22:30:00', '260.00'
 '22', '0', '24:00:00', '260.00');

CREATE TABLE `schedule` (
  `id`        bigint NOT NULL AUTO_INCREMENT,
  `date`      date   NOT NULL,
  `id_seance` bigint NOT NULL,
  `id_film`   bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_id_seances_in_schedule_idx` (`id_seance`),
  KEY `fk_films_in_schedule_idx` (`id_film`),
  CONSTRAINT `fk_films_in_schedule` FOREIGN KEY (`id_film`) REFERENCES `films` (`id`),
  CONSTRAINT `fk_seances_in_schedule` FOREIGN KEY (`id_seance`) REFERENCES `seances` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `j9-4`.`schedule` (`id`,`date`,`id_seance`,`id_film`) VALUES
('1', '2022-01-23',  '1', '1'
 '2', '2022-01-23',  '2', '2'
 '3', '2022-01-23',  '3', '9'
 '4', '2022-01-23',  '4', '3'
 '5', '2022-01-23',  '5', '4'
 '6', '2022-01-23',  '6', '5'
 '7', '2022-01-23',  '7', '6'
 '8', '2022-01-23',  '8', '3'
 '9', '2022-01-23',  '9', '4'
'10', '2022-01-23', '10', '5'
'11', '2022-01-23', '11', '6');

CREATE TABLE `tickets` (
  `id`          bigint NOT NULL AUTO_INCREMENT,
  `id_schedule` bigint NOT NULL,
  `row`         int    NOT NULL, -- рад в зале (не используется в ДЗ)
  `column`      int    NOT NULL, -- место в ряду (не используется в ДЗ)
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_id_schedule_in_tickets_idx` (`id_schedule`),
  CONSTRAINT `fk_id_schedule_in_tickets` FOREIGN KEY (`id_schedule`) REFERENCES `schedule` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `j9-4`.`tickets` (`id`,`id_schedule`,`row`,`column`) VALUES
('1',  '1',   '10', '11'
 '2',  '1',   '10', '12'
 '3',  '4',   '14',  '8'
 '4',  '7',    '8', '23'
 '5', '11',   '19',  '7');

-- Расписание на 23 января 2022 года (воскресение) :
SELECT	schedule.id, schedule.date AS `Дата`, seances.time AS `Начало просмотра`,
		films.title AS `Название фильма`, films.duration AS `Продолжительность`,
        seances.price AS `Цена билета`
FROM schedule, seances, films
WHERE schedule.date = 20220123 AND
	  schedule.id_seance = seances.id AND
      schedule.id_film = films.id;

-- проданные билеты на 2022-01-23
SELECT  tickets.id, tickets.id_schedule,
		films.title AS `Название фильма`,
        tickets.row AS `Ряд`, tickets.column AS `Место`,
		schedule.date AS `Дата`, seances.time AS `Начало просмотра`,
        seances.price AS `Цена`
FROM tickets, seances, films, schedule
WHERE films.id = schedule.id_film AND
	  schedule.id = tickets.id_schedule AND
      schedule.id_seance = seances.id AND
      schedule.date = 20220123;

-- з1: ошибки в расписании (фильмы накладываются друг на друга), отсортированные по возрастанию времени.
-- Выводить надо колонки
-- «фильм 1», «время начала», «длительность», «фильм 2», «время начала», «длительность»;
SELECT v1.date, v1.title title1, v1.time time1, v1.duration duration1,
				v2.title title2, v2.time time2, v2.duration duration2
FROM
(SELECT sc.date, f.title, ss.time, f.duration
 FROM schedule sc, films f, seances ss
 WHERE (sc.id_seance = ss.id) and (sc.id_film = f.id)) v1
join
(SELECT sc.date, f.title, ss.time, f.duration
 FROM schedule sc, films f, seances ss
 WHERE (sc.id_seance = ss.id) and (sc.id_film = f.id)) v2
	  ON v1.date = v2.date AND
         v1.time < v2.time AND
         v1.time + v1.duration > v2.time
;

-- з2: перерывы 30 минут и более между фильмами — выводить по уменьшению длительности перерыва.
-- Колонки «фильм 1», «время начала», «длительность», «время начала второго фильма», «длительность перерыва»;
SELECT * FROM
(	SELECT	v1.date, v1.title title1, v1.time time1, v1.duration duration1,
					 v2.title title2, v2.time time2, SUBTIME(v2.time, ADDTIME(v1.time, v1.duration)) pause_duration
	FROM
	(SELECT sc.id, sc.date, f.title, ss.time, f.duration
     FROM schedule sc, films f, seances ss
     WHERE (sc.id_seance = ss.id) and (sc.id_film = f.id)) v1
	join
	(SELECT sc.id, sc.date, f.title, ss.time, f.duration
     FROM schedule sc, films f, seances ss
     WHERE (sc.id_seance = ss.id) and (sc.id_film = f.id)) v2
	 	  ON
          v1.id +1 = v2.id	-- фильмы в расписании идут друг за другом
	ORDER BY pause_duration DESC
) p
WHERE p.pause_duration >= TIME(003000)
;

-- з3: список фильмов, для каждого — с указанием общего числа посетителей за все время, среднего числа зрителей за сеанс и общей суммы сборов по каждому фильму (отсортировать по убыванию прибыли).
-- Внизу таблицы должна быть строчка «итого», содержащая данные по всем фильмам сразу;
SELECT	a.f_id `f_id`,
        IF(GROUPING(a.f_id), "Итого", a.title) `title`,
		@T := COUNT(a.t_id) `tickets_sold_total` ,
        SUM(a.price) `total_cache`,
        IF (@I := @T / COUNT(DISTINCT a.sc_id), @I, 0) `tickets_per_seance_average`
FROM
(	SELECT	f.id `f_id`, f.title, sc.id `sc_id`, t.id `t_id`,
			IF (ss.price AND t.id, ss.price, 0) `price`
	FROM tickets t RIGHT JOIN schedule sc
		 ON sc.id = t.id_schedule
		 JOIN seances ss
			 ON ss.id = sc.id_seance
			 RIGHT JOIN films f
				ON f.id = sc.id_film
	ORDER BY `f_id`
) a
GROUP BY a.f_id WITH ROLLUP
;

-- з4: число посетителей и кассовые сборы, сгруппированные по времени начала фильма:
-- с 9 до 15, с 15 до 18, с 18 до 21, с 21 до 00:00 (сколько посетителей пришло с 9 до 15 часов и т.д.).
SELECT a.time , a.holiday , SUM(a.tickets_per_seance) , SUM(a.cache_per_seance)
FROM
(
	SELECT	CASE WHEN ss.time < TIME(150000) THEN TIME(090000)
				 WHEN ss.time < TIME(180000) THEN TIME(150000)
				 WHEN ss.time < TIME(210000) THEN TIME(180000)
				 ELSE TIME(210000) END `time`,
			IF(ss.holiday,"Выходной","Будни") `holiday`,
			COUNT(t.id) `tickets_per_seance`,
			ss.price * COUNT(t.id) `cache_per_seance`
	FROM seances ss -- LEFT
		 JOIN schedule sc
		 ON sc.id_seance = ss.id -- LEFT
			JOIN tickets t
			ON t.id_schedule = sc.id
	GROUP BY ss.time, ss.holiday
	ORDER BY `time`
) a
GROUP BY a.time
;

