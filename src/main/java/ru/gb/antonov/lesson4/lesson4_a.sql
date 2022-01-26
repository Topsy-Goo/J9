
-- з1: ошибки в расписании (фильмы накладываются друг на друга), отсортированные по возрастанию времени.
-- Выводить надо колонки
-- «фильм 1», «время начала», «длительность», «фильм 2», «время начала», «длительность»;
SELECT sc1.date, f1.title title1, ss1.time time1, f1.duration duration1,
				 f2.title title2, ss2.time time2, f2.duration duration2
FROM
	schedule sc1 JOIN schedule sc2 ON sc1.date = sc2.date
		JOIN seances ss1 ON sc1.id_seance = ss1.id
			JOIN seances ss2 ON sc2.id_seance = ss2.id  AND ss1.time < ss2.time
				JOIN films f1 ON sc1.id_film = f1.id    AND ss1.time + f1.duration > ss2.time
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