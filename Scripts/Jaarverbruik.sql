DROP VIEW "APP"."JAARVERBRUIK";

CREATE VIEW "APP"."JAARVERBRUIK" AS (
	SELECT
		  m.id
		, m.metersoort
		, v.jaar
		, sum(v.verbruik) AS  SOM
	FROM
		app.MAANDVERBRUIK AS v
	LEFT JOIN
		app.METERSOORTEN AS m
	ON
		v.ID_METERSOORT = m.ID
	GROUP BY
		  m.ID
		, m.METERSOORT
		, v.JAAR
);
