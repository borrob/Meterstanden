package main.java.meterstanden.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import main.java.meterstanden.hibernate.HibernateUtil;
import main.java.meterstanden.model.Metersoorten;
import main.java.meterstanden.model.Meterstanden;

public class Month {
	
	private static final Logger log = Logger.getLogger(Month.class);
	
	public static float getMonth(int month, int year, Metersoorten ms){

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Meterstanden first = getMeterstand(session, month, year, ms, true);
		Meterstanden last= getMeterstand(session, month, year, ms, false);
		
		session.close();
		
		if(first.getDatum().equals(last.getDatum())){
			log.debug("The dates are equal.");
			return last.getWaarde();
		} else {
			long timeBetween= last.getDatum().getTime() - first.getDatum().getTime();
			Date maandDag = parseDatum("01-" + Integer.toString(month) + "-" + Integer.toString(year));
			long timeToStart = maandDag.getTime() - first.getDatum().getTime();
			log.trace("timeBetween: " + Long.toString(timeBetween) + " - timeToStart: " + Long.toString(timeToStart));
			
			float result = first.getWaarde() + (float)timeToStart/timeBetween * (last.getWaarde() - first.getWaarde());
			
			log.trace("First: " + Float.toString(first.getWaarde()));
			log.trace("Result: " + Float.toString(result));
			log.trace("Last: " + Float.toString(last.getWaarde()));
			
			return result;
		}
	}
	
	public static float getNextMonth(int month, int year, Metersoorten ms){
		if (month == 12){
			month = 0;
			year ++;
		}
		return getMonth(month + 1, year, ms);
	}

	public static float getMonthUsage(int month, int year, Metersoorten ms){
		float start = getMonth(month, year, ms);
		float end = getNextMonth(month, year, ms);
		return end - start;
	}
	
	private static Meterstanden getMeterstand(Session s, int month, int year, Metersoorten ms, boolean before){
		
		StringBuilder hql = new StringBuilder("from Meterstanden m ");	
		hql.append("where m.metersoort = :metersoort ");
		
		if(before){
			hql.append("and datum <= '").append(String.valueOf(year)).append("-").append(String.valueOf(month)).append("-01'");
			hql.append(" order by datum desc");
		} else {
			hql.append("and datum >= '").append(String.valueOf(year)).append("-").append(String.valueOf(month)).append("-01'");
			hql.append(" order by datum asc");
		}
		
		Query query = s.createQuery(hql.toString());
		query.setParameter("metersoort", ms);
		query.setMaxResults(1);
		Meterstanden result = (Meterstanden)query.getResultList().get(0);
		//TODO: add try, catch and raise error
		
		return result;
	}
	
	private static Date parseDatum(String datumstring){
		//TODO: javadoc
		
		Date datum = null;
		SimpleDateFormat sdf = new SimpleDateFormat ("dd-MM-yyyy");
		
		try {
			datum = sdf.parse(datumstring);
		} catch (ParseException e) {
			log.error("Dateformat wrong - error: " + e.toString());
			// TODO notify user
		}
		return datum;
	}

}
