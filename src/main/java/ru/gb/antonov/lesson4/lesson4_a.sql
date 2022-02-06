
-- з1: ошибки в расписании (фильмы накладываются друг на друга), отсортированные по возрастанию времени.
-- Выводить надо колонки
-- «фильм 1», «время начала», «длительность», «фильм 2», «время начала», «длительность»;
SELECT sc1.date, f1.title title1, ss1.time time1, f1.duration duration1,
				 f2.title title2, ss2.time time2, f2.duration duration2
FROM
	schedule sc1 JOIN schedule sc2 ON sc1.date = sc2.date
		JOIN seances ss1 ON sc1.id_seance = ss1.id
			JOIN seances ss2 ON sc2.id_seance = ss2.id  AND ss1.time < ss2.time
				JOIN films f1 ON sc1.id_film = f1.id    AND ADDTIME(ss1.time, f1.duration) > ss2.time
					JOIN films f2 ON sc2.id_film = f2.id
ORDER BY time1
;

-- з2: перерывы 30 минут и более между фильмами — выводить по уменьшению длительности перерыва.
-- Колонки «фильм 1», «время начала», «длительность», «время начала второго фильма», «длительность перерыва»;
SELECT * FROM
(	SELECT sc1.date, f1.title title1,     ss1.time time1, f1.duration duration1,
					 f2.title title2, MIN(ss2.time) time2,
					 SUBTIME(ss2.time, ADDTIME(ss1.time, f1.duration)) pause_duration
	FROM
		schedule sc1 JOIN schedule sc2   ON sc1.date = sc2.date
		JOIN seances ss1 ON sc1.id_seance = ss1.id
		JOIN seances ss2 ON sc2.id_seance = ss2.id  AND ss1.time < ss2.time
			JOIN films f1 ON sc1.id_film = f1.id
			JOIN films f2 ON sc2.id_film = f2.id
GROUP BY time1
)a
WHERE pause_duration >= TIME(003000)
ORDER BY pause_duration DESC
;


-- з3: список фильмов, для каждого — с указанием общего числа посетителей за все время, среднего числа зрителей за сеанс и общей суммы сборов по каждому фильму (отсортировать по убыванию прибыли).
-- Внизу таблицы должна быть строчка «итого», содержащая данные по всем фильмам сразу;
-- ** По сравнению с вариантом из файла lesson4.sql здесь именено вычисление поля tickets_per_seance_average в строчке Итого. В таком варианте мы получаем возможность задавать любой алгоритм вычисления этого значения. Сейчас значение вычисляется как среднее значение по всем строкам.

SELECT	f_id,
		IF(GROUPING(f_id), "Итого", title) title,
        SUM(tickets_sold_total) `tickets_sold_total`,
        SUM(total_cache) `total_cache`,
        AVG(tickets_per_seance_average) as `tickets_per_seance_average`
FROM
(	SELECT	a.f_id `f_id`,
			a.title `title`,
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
	)a
	GROUP BY a.f_id
)b
GROUP BY f_id WITH ROLLUP
;