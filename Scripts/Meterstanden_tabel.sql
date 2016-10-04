DROP TABLE "APP"."METERSTANDEN";

CREATE TABLE "APP"."METERSTANDEN" (
	id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	datum date,
	waarde float,
	omschrijving varchar(255),
	id_metersoort integer REFERENCES "APP"."METERSOORTEN" (id)
);

insert INTO "APP"."METERSTANDEN" (
	datum,
	waarde,
	omschrijving,
	id_metersoort
) VALUES
	('2016-05-03', 55467, 'Start', 1),
	('2016-05-03', 1714, 'Start', 2),
	('2016-05-03', 793, 'Start', 3),
	('2016-06-06', 55561, '', 1),
	('2016-06-04', 1719, '', 2),
	('2016-06-08', 927, '', 3),
	('2016-07-06', 55715, '', 1),
	('2016-07-04', 1724, '', 2),
	('2016-07-08', 1052, '', 3),
	('2016-08-06', 55909, '', 1),
	('2016-08-04', 1729, '', 2),
	('2016-08-08', 1184, '', 3)
;

SELECT * FROM "APP"."METERSTANDEN";