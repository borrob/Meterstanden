package main.java.meterstanden.util;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import main.java.meterstanden.hibernate.HibernateUtil;
import main.java.meterstanden.model.Maandverbruik;
import main.java.meterstanden.model.Metersoorten;
import main.java.meterstanden.model.Meterstanden;

public class UpdateVerbruik {
	//TODO javadoc
	
	private static Logger log = Logger.getLogger(UpdateVerbruik.class);
	
	/**
	 * Update the Maandverbruik based on this meterstand.
	 * 
	 * The maandverbruik is calculated with the current Meterstand and the meterstanden
	 * before and after this one.
	 * 
	 * @param ms the Meterstand on which the update is based.
	 * @throws IndexOutOfBoundsException When no previous or next Meterstand can be found.
	 */
	public static void updateMeterverbruik(Meterstanden ms) throws IndexOutOfBoundsException {
		log.trace("Going to update the maandverbruik based on meterstand: " + ms.toString());
		int[] my = parseMonthYear(ms);
		Meterstanden[] fl = new Meterstanden[2];
		try {
			fl[0] = getPreviousOrNextMeterstand(ms, true);
			fl[1] = getPreviousOrNextMeterstand(ms, false);
		} catch (IndexOutOfBoundsException nullValuesWillBeDealtWithLater){
			log.debug("At least one of previous and next meterstand is null.");
		}
		if (fl[0]!= null && fl[1]!=null){
			log.trace("Going to update maandverbruiken.");
			try {
				loopUpdate(fl, ms);
			} catch (IndexOutOfBoundsException e){
				log.debug("Some error with updating maandverbruik: " + e.toString());
				throw e;
			}
			return;
		}
		if (fl[0]==null){
			log.debug("Could not find a starting meterstand.");
			
			//Deleting all maandverbruiken before this meterstand.
			deleteMeterverbruiken(ms, my, true);
			
			//Update all maandverbruik from this meterstand to the month of nextMeterstand
			fl[0] = ms;
		}
		if (fl[1]==null){
			log.debug("Could nog find an ending meterstand.");
			
			//Deleting all maandverbruiken after this meterstand.
			deleteMeterverbruiken(ms, my, false);
			
			//Update all maandverbruik from the previous meterstand to this meterstand.
			fl[1] = ms;
		}
		try {
			loopUpdate(fl, ms);
		} catch (IndexOutOfBoundsException e){
			log.debug("At least one update didn't succeed. " + e.toString());
			throw e;
		}
	}
	
	public static void updateMeterverbruik(Long m){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Meterstanden ms = session.get(Meterstanden.class, m);
		updateMeterverbruik(ms);
	}
	
	/**
	 * Get the year and mont from the meterstand.
	 * 
	 * @param ms the Meterstand from which the year and month are to be extracted.
	 * @return int[] where the first member is the month (JAN=1) and the second member the year.
	 */
	private static int[] parseMonthYear(Meterstanden ms){
		Calendar msCal = Calendar.getInstance();
		msCal.setTime(ms.getDatum());
		int maand = msCal.get(Calendar.MONTH)+1; //zero-based
		int jaar = msCal.get(Calendar.YEAR);
		
		int[] result = new int[2];
		result[0] = maand;
		result[1] = jaar;
		
		return result;
	}
	
	/**
	 * Update all Maandverbruiken
	 * 
	 * @param fl The first and last meterstand to use for the update.
	 * @param ms The current meterstand.
	 */
	private static void loopUpdate(Meterstanden[] fl, Meterstanden ms){
		int[] firstDate = parseMonthYear(fl[0]);
		int[] lastDate = parseMonthYear(fl[1]);
		
		int m = firstDate[0];
		int j = firstDate[1];
		
		while(j<lastDate[1] || (j==lastDate[1] && m<=lastDate[0]) ){

			deleteMeterverbruik(m,j,ms.getMetersoort()); //delete old entry first	
			try {
					float verbruik = Month.getMonthUsage(m, j, ms.getMetersoort());
					log.debug("Verbruik van " + Integer.toString(j) + "-" + Integer.toString(m) + ": " + Float.toString(verbruik));
					Maandverbruik mv = new Maandverbruik(j, m, ms.getMetersoort(), verbruik);
					HibernateUtil.persistMaandverbruik(mv);
				} catch (IndexOutOfBoundsException e){
					log.debug("Maandverbruik for " + Integer.toString(j) + "-" + Integer.toString(m) +
							" could not be calculated.");
					throw e;
				}
				
			j = m==12?j+1:j;
			m = m==12?1:m+1;
		}
	}
	
	/**
	 * Delete the maandverbruik.
	 * 
	 * @param m the month of the maandverbruik to be deleted.
	 * @param j the year of the maandverbruik to be deleted.
	 * @param ms the metersoort of the maandverbruik to be delete.
	 */
	private static void deleteMeterverbruik(int m, int j, Metersoorten ms){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query q = session.createQuery("from Maandverbruik M where M.metersoort = :myMetersoort and M.maand = :myMaand and M.jaar = :myJaar");
		q.setParameter("myMetersoort", ms);
		q.setParameter("myMaand", m);
		q.setParameter("myJaar", j);
		List<?> rl = q.getResultList();
		session.close();
		Iterator<?> itr = rl.listIterator();
		while (itr.hasNext()){
			Maandverbruik mv = (Maandverbruik)itr.next();
			log.info("Deleting maandverbruikd: " + mv.toString());
			HibernateUtil.deleteMaandverbruik(mv.getId());
		}
	}
	
	/**
	 * Delete the meterverbruik, including before or after the current meterstand.
	 * 
	 * @param ms The current meterstand on which the Maandverbruik is based on.
	 * @param monthYear int[] month and year to start deleting Maandverbruiken.
	 * @param before Delete until (true) or after the monthYear.
	 */
	private static void deleteMeterverbruiken(Meterstanden ms, int[] monthYear, boolean before){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		//delete same year, but the rest of the months
		StringBuilder hql = new StringBuilder(128);
		hql.append("delete from Maandverbruik");
		if (before){
			hql.append(" where maand <= :myMaand");
		} else {
			hql.append(" where maand >= :myMaand");
		}
		hql.append(" and jaar = :myJaar");
		hql.append(" and metersoort = :myMeter");
		Query q = session.createQuery(hql.toString());
		q.setParameter("myMaand", monthYear[0]);
		q.setParameter("myJaar", monthYear[1]);
		q.setParameter("myMeter", ms.getMetersoort());
		int numberDeleted = q.executeUpdate();
		
		//delete other years
		StringBuilder hql2 = new StringBuilder(128);
		hql2.append("delete from Maandverbruik");
		if (before){
			hql2.append(" where jaar < :myJaar");
		} else {
			hql2.append(" where jaar > :myJaar");
		}
		hql2.append(" and metersoort = :myMeter");
		Query q2 = session.createQuery(hql2.toString());
		q2.setParameter("myJaar", monthYear[1]);
		q2.setParameter("myMeter", ms.getMetersoort());
		int numberDeleted2 = q2.executeUpdate();
		
		session.getTransaction().commit();
		session.close();
		
		log.info("Deleted " + Integer.toString(numberDeleted + numberDeleted2) + " records of maandverbruik.");	
	}
	
	/**
	 * Get the previous or next meterstand before/after the current one.
	 * 
	 * @param ms The current meterstand
	 * @param prev True: get the meterstand before this one, fals: get the meterstand of this one.
	 * @return the Meterstand before/after the current one.
	 */
	private static Meterstanden getPreviousOrNextMeterstand(Meterstanden ms, boolean prev){
		Meterstanden result;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuilder hql = new StringBuilder(128);
		hql.append("from Meterstanden m");
		hql.append(" where m.metersoort = :myMeter");
		if (prev){
			hql.append(" and m.datum < :myDatum");
			hql.append(" order by m.datum desc");
		} else {
			hql.append(" and m.datum > :myDatum");
			hql.append(" order by m.datum asc");
		}
		Query q = session.createQuery(hql.toString());
		q.setParameter("myMeter", ms.getMetersoort());
		q.setParameter("myDatum", ms.getDatum());
		q.setMaxResults(1);

		try {
			result = (Meterstanden)q.getResultList().get(0);
		} catch (IndexOutOfBoundsException e){
			log.debug("Error for getting the meterstand for metersoort: " +
					ms.toString() + ". Perhaps it doesn't exists?");
			throw e;
		}
		
		return result;
	}

	/**
	 * update all verbruiken voor this metersoort
	 * 
	 * @param ms the metersoort to update all verbruiken
	 */
	public static void updateAllMS(Metersoorten ms) {
		log.debug("Going to update all verbruiken for metersoort: " + ms.toString());
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		StringBuilder hql = new StringBuilder();
		hql.append("from Meterstanden m");
		hql.append(" where m.metersoort = :myMs");
		Query q = session.createQuery(hql.toString());
		q.setParameter("myMs", ms);
		List<?> qResultlist = q.getResultList();
		
		session.close();
		
		Iterator<?> qIterator = qResultlist.iterator();
		while (qIterator.hasNext()){
			Meterstanden myMeterstand = (Meterstanden)qIterator.next();
			try {
				updateMeterverbruik(myMeterstand);
			} catch (IndexOutOfBoundsException ignore){
				//pass
			}
		}
	}
	
	/**
	 * Update all verbruiken for all meterstanden.
	 */
	public static void updateAll(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		StringBuilder hql = new StringBuilder();
		hql.append("from Metersoorten m");
		Query q = session.createQuery(hql.toString());
		List<?> qResultList = q.getResultList();
		session.close();
		
		Iterator<?> qResultListIterator = qResultList.iterator();
		while (qResultListIterator.hasNext()){
			Metersoorten ms = (Metersoorten)qResultListIterator.next();
			updateAllMS(ms);
		}
	}
}
