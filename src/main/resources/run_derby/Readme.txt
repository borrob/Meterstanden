Voor het gemak gebruiken we hier een derby database. De jar-files kunnen vanaf de website gedownload worden: http://db.apache.org/derby/derby_downloads.html

De database is aangemaakt als:

java -jar lib/derbyrun.jar ij

>> connect 'jdbc:derby:meterstanden;user=u;password=p;create=true';
>> exit;

Daarna kan met een jdb sql-connectie de setup scripts gedraaid worden.

De server kan vervolgens opgestart worden met

java -jar lib/derbyrun.jar server start

In de Scripts folder staan setup scripts om de tabellen aan te maken.