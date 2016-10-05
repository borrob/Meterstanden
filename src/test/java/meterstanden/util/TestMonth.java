package test.java.meterstanden.util;

import static org.junit.Assert.*;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.Test;

import main.java.meterstanden.hibernate.HibernateUtil;
import main.java.meterstanden.model.Metersoorten;
import main.java.meterstanden.model.Meterstanden;
import main.java.meterstanden.util.Month;

public class TestMonth {
	
	private static Logger log = Logger.getLogger(TestMonth.class);

	@Test
	public void testMonth() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Metersoorten ms = session.get(Metersoorten.class, 1L);
		float calculatedMeterstand = Month.getMonth(6, 2016, ms);
				
		Query meterstandenQuery = session.createQuery(
				"from Meterstanden m where m.metersoort = :mes order by datum asc");
		meterstandenQuery.setParameter("mes", ms);
		Meterstanden lowest = (Meterstanden) meterstandenQuery.getResultList().get(0);
		
		Query meterstandenQuery2 = session.createQuery(
				"from Meterstanden m where m.metersoort = :mes order by datum desc");
		meterstandenQuery2.setParameter("mes", ms);
		Meterstanden highest = (Meterstanden) meterstandenQuery2.getResultList().get(0);
		
		session.close();
		
		log.debug("Lowest - calculated - Highest: " + 
				Float.toString(lowest.getWaarde()) + " - " +
				Float.toString(calculatedMeterstand) + " - " +
				Float.toString(highest.getWaarde()));
		
		assertTrue(calculatedMeterstand > 0);
		assertTrue(calculatedMeterstand >= lowest.getWaarde());
		assertTrue(calculatedMeterstand <= highest.getWaarde());
	}

}
