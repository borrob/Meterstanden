package test.java.meterstanden.hibernate;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import org.junit.Test;

import main.java.meterstanden.hibernate.HibernateUtil;
import main.java.meterstanden.model.Metersoorten;
import main.java.meterstanden.model.Meterstanden;

public class TestUtil {
	
	/**
	 * Test to access Metersoorten.
	 */
	@Test
	public void TestMetersoorten(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Query query = session.createQuery("from Metersoorten");
			List<?> rl = query.getResultList();
			rl.get(0);
		} catch (Exception e){
			fail("Error for getting the Metersoorten.");
		} finally {
			session.close();
		}
	}
	
	/**
	 * Test to access Meterstanden.
	 */
	@Test
	public void TestMeterstanden(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Query query = session.createQuery("from Meterstanden");
			List<?> rl = query.getResultList();
			rl.get(0);
		} catch (Exception e){
			fail("Error for getting the Meterstanden.");
		} finally {
			session.close();
		}
	}
	
	/**
	 * Test to add and delete a meterstand.
	 */
	@Test
	public void TestAddAndDeleteMeterstanden(){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Metersoorten theMetersoort = session.get(Metersoorten.class, 1L);
		session.close();
		Date theDate = new Date();
		float theWaarde = 123.456F;
		String theOmschrijving = "TEST";
		
		Meterstanden ms = new Meterstanden(theDate, theWaarde, theOmschrijving, theMetersoort);
		
		HibernateUtil.persistMeterstand(ms);
			
		//check if the meterstand is really added
		long theId = ms.getId();
		Session session2 = HibernateUtil.getSessionFactory().openSession();
		Meterstanden shouldExists = session2.get(Meterstanden.class, theId);
		session2.close();
		assertNotNull(shouldExists);
		
		HibernateUtil.deleteMeterstand(theId);
		
		//check if it is really deleted
		Session session3 = HibernateUtil.getSessionFactory().openSession();
		Meterstanden theMeterstand = session3.get(Meterstanden.class, theId);
		session3.close();
		assertNull(theMeterstand);
		
		session.close();
	}
	
	//TODO: ADD test cases for calculating the mesterstandverbruik

}
