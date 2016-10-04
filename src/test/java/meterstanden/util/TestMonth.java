package test.java.meterstanden.util;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.Test;

import main.java.meterstanden.hibernate.HibernateUtil;
import main.java.meterstanden.model.Metersoorten;
import main.java.meterstanden.util.Month;

public class TestMonth {
	
	private static Logger log = Logger.getLogger(TestMonth.class);

	@Test
	public void testMonth() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Metersoorten ms = session.get(Metersoorten.class, (long)1);
		log.debug(Float.toString(Month.getMonth(6, 2016, ms)));
		fail("Not yet implemented");
	}

}
