DROP TABLE "APP".metersoorten;

CREATE TABLE "APP".metersoorten (
	id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	metersoort varchar(255),
	unit varchar(255)
);

INSERT INTO "APP".metersoorten (metersoort, unit) VALUES ('gas', 'm3'), ('water', 'm3'), ('elektriciteit', 'kWh');

SELECT * FROM "APP".metersoorten;