DROP TABLE "APP"."MAANDVERBRUIK";

CREATE TABLE "APP"."MAANDVERBRUIK" (
	id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	jaar int,
	maand int,
	id_metersoort integer REFERENCES "APP"."METERSOORTEN" (id),
	verbruik float
);