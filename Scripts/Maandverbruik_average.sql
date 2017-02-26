DROP VIEW IF APP.MAANDVERBRUIK_AVG;

CREATE VIEW APP.MAANDVERBRUIK_AVG (id_metersoort, maand, average) AS (
	SELECT id_metersoort, maand, avg(verbruik) AS average
	FROM APP.MAANDVERBRUIK
	GROUP BY id_metersoort, maand
);
