DROP TABLE "APP".metersoorten;

CREATE TABLE "APP".metersoorten (
	id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	metersoort varchar(255)
);

INSERT INTO "APP".metersoorten (metersoort) VALUES ('gas'), ('water'), ('electricitiet');

SELECT * FROM "APP".metersoorten;