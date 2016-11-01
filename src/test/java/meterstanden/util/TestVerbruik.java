package test.java.meterstanden.util;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.junit.Test;

import main.java.meterstanden.hibernate.HibernateUtil;
import main.java.meterstanden.model.Maandverbruik;
import main.java.meterstanden.model.Metersoorten;
import main.java.meterstanden.model.Meterstanden;
import main.java.meterstanden.util.UpdateVerbruik;

public class TestVerbruik {

	/**
	 * Test the calculation of usage.
	 */
	@Test
	public void testUsage() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Calendar calStart = Calendar.getInstance();
		calStart.set(2000, 1, 1);
		Metersoorten mSoort = session.get(Metersoorten.class, 1L);
		Meterstanden msStart = new Meterstanden(calStart.getTime(), 100F, "test", mSoort);
		HibernateUtil.persistMeterstand(msStart);
		
		Calendar calEnd = Calendar.getInstance();
		calEnd.set(2000, 2, 1);
		Meterstanden msEnd = new Meterstanden(calEnd.getTime(), 200F, "test", mSoort);
		HibernateUtil.persistMeterstand(msEnd);
		
		UpdateVerbruik.updateMeterverbruik(msStart);
		
		StringBuilder hql = new StringBuilder(128);
		hql.append("from Maandverbruik m");
		hql.append(" where m.jaar = 2000");
		hql.append(" and m.maand = 2");
		hql.append(" and m.metersoort = :myMeter");
		Query q = session.createQuery(hql.toString());
		q.setParameter("myMeter", mSoort);
		List<?> mvList = q.getResultList();
		Iterator<?> mvListIt = mvList.iterator();
		Maandverbruik mv = (Maandverbruik)mvListIt.next();
		
		assertEquals(100F, mv.getVerbruik(), 0.001);
		
		//cleanup
		session.beginTransaction();
		session.delete("Maandverbruik", mv);
		session.delete("Meterstanden", msStart);
		session.delete("Meterstanden", msEnd);
		session.getTransaction().commit();
		
		session.close();

	}
	
	/**
	 * Test the calculation of usage over a longer period.
	 */
	@Test
	public void testUsageLonger() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Calendar calStart = Calendar.getInstance();
		calStart.set(2000, 1, 1);
		Metersoorten mSoort = session.get(Metersoorten.class, 1L);
		Meterstanden msStart = new Meterstanden(calStart.getTime(), 100F, "test", mSoort);
		HibernateUtil.persistMeterstand(msStart);
		
		Calendar calBetween = Calendar.getInstance();
		calBetween.set(2000, 6, 1);
		Meterstanden msBetween = new Meterstanden(calBetween.getTime(), 700F, "test", mSoort);
		HibernateUtil.persistMeterstand(msBetween);
		
		Calendar calEnd = Calendar.getInstance();
		calEnd.set(2000, 11, 31);
		Meterstanden msEnd = new Meterstanden(calEnd.getTime(), 1300F, "test", mSoort);
		HibernateUtil.persistMeterstand(msEnd);
		
		UpdateVerbruik.updateMeterverbruik(msBetween);
		
		StringBuilder hql = new StringBuilder(128);
		hql.append("from Maandverbruik m");
		hql.append(" where m.jaar = 2000");
		hql.append(" and m.maand = 2");
		hql.append(" and m.metersoort = :myMeter");
		Query q = session.createQuery(hql.toString());
		q.setParameter("myMeter", mSoort);
		List<?> mvList = q.getResultList();
		Iterator<?> mvListIt = mvList.iterator();
		Maandverbruik mv = (Maandverbruik)mvListIt.next();
		
		assertEquals(115.263F, mv.getVerbruik(), 0.001);
		
		//cleanup
		session.beginTransaction();
		StringBuilder hqlDel = new StringBuilder(128);
		hqlDel.append("delete Maandverbruik m");
		hqlDel.append(" where m.jaar = 2000");
		hqlDel.append(" and m.metersoort = :myMeter");
		Query qDel = session.createQuery(hqlDel.toString());
		qDel.setParameter("myMeter", mSoort);
		qDel.executeUpdate();
		session.delete("Meterstanden", msStart);
		session.delete("Meterestanden", msBetween);
		session.delete("Meterstanden", msEnd);
		session.getTransaction().commit();
		
		session.close();

	}

}
