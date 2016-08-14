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
	('2016-05-03', 5522, 'Start', 1),
	('2016-05-03', 1603, 'Start', 2),
	('2016-05-03', 339200, 'Start', 3),
	('2016-06-06', 5622, '', 1),
	('2016-06-04', 1623, '', 2),
	('2016-06-08', 339450, '', 3)
;

SELECT * FROM "APP"."METERSTANDEN";