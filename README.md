# Meterstanden

A nice solution to keep track of your meters (gas, water, electricity).

## Technology

This started out as a test project to get my hands dirty with Hibernate and how to connect java to a database. I started out by building the web-application using jsp and jsp tags. That was a nice exercise, but I believe not the wat to go anymore. I switched to angular2. This project made a great excuse to learn angular. For the graphs in the application, I opted for `chart.js`.

The database uses the derby database enginge. I didn't want to setup a complete database server, so I went for something that you can run when you need it en stop when you don't need it. My preference would have been to use SQLite, but that had some issues connecting to Hibernate. If you want to use your own database, you'll have to configure the Hibernate-config.

## How to build

Load the maven project by opening the pom.xml in the root directory. The java source code is available in `src/main/java`. Check the settings in the `src/main/resources` of `log4j` and `hibernate`. The web application uses angular2 and the source code is in `src/main/javascript`. The build commands are configured in `package.json`. By `npm run build` you can build a debug version of the app. With `npm run deploy` you build a deploy version of the app and copy it to the webcontent directory of the java application.

## How to run

Start you database server (in my case: derby), deploy the war and you're good to go.

### How to start

At the first start up, you'll need to initialise the database. Check out the `Srcipts` directory.

## Ideas/suggestions

Ideas and suggestions are welcome. Feel free to fork the repo and create your own version of this Meter tracking Web Application!
