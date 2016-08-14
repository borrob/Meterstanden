package meterstanden.hibernate;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import javax.persistence.Query;

import meterstanden.model.Metersoorten;
import meterstanden.model.Meterstanden;


/**
 * @author rob
 */
@SuppressWarnings("deprecation")
public class HibernateTest {

	/**
	 * A test class to connect to the database.
	 * 
	 * This class runs a couple of queries in different ways. It's a good exercise.
	 * 
	 * @param args main arguments
	 */
	public static void main(String[] args){
		
		Logger log = Logger.getLogger(HibernateTest.class);
		log.error("----------------STARTUP " + HibernateTest.class.getName() + " ----------------");
		log.warn("Hallo!");
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		log.info("Getting the data via a SQL-select statement.");
		SQLQuery<?> q = session.createSQLQuery("SELECT * FROM metersoorten");
		q.addEntity(Metersoorten.class);
		List<?> results = q.list();
		Iterator<?> qit = results.iterator();
		while (qit.hasNext()){
			System.out.println(((Metersoorten)qit.next()).getMetersoort());
		}
		
		log.info("Getting the data via HQL");
		Query query = session.createQuery("from Metersoorten");
		List<?> rl = query.getResultList();
		Iterator<?> itrl = rl.listIterator();
		while (itrl.hasNext()){
			System.out.println(((Metersoorten)itrl.next()).getMetersoort());
		}
		
		log.info("Add a WHERE statement");
		Query query2 = session.createQuery("from Metersoorten M where M.metersoort = :myMetersoort");
		query2.setParameter("myMetersoort", "water");
		List<?> rl2 = query2.getResultList();
		Iterator<?> itrl2 = rl2.listIterator();
		while (itrl2.hasNext()){
			System.out.println(((Metersoorten)itrl2.next()).toString());
		}
		log.info("Getting the data of a single row with the ID");
		Metersoorten ms = session.get(Metersoorten.class, 1);
		System.out.println(ms.getMetersoort());
		
		log.info("Getting meterstanden");;
		Query meterstandenQuery = session.createQuery("from Meterstanden");
		List<?> rlMSQ = meterstandenQuery.getResultList();
		Iterator<?> rlMSQ_it = rlMSQ.listIterator();
		while (rlMSQ_it.hasNext()){
			System.out.println(((Meterstanden)rlMSQ_it.next()).toString());
		}
		
		log.info("Getting meterstanden for 'water'");;
		Query meterstandenQuery2 = session.createQuery("from Meterstanden m where m.metersoort = :mes");
		Metersoorten ms_1 = session.get(Metersoorten.class, 1); //assuming 1 is water
		meterstandenQuery2.setParameter("mes", ms_1);
		List<?> rlMSQ2 = meterstandenQuery2.getResultList();
		Iterator<?> rlMSQ_it2 = rlMSQ2.listIterator();
		while (rlMSQ_it2.hasNext()){
			System.out.println(((Meterstanden)rlMSQ_it2.next()).toString());
		}
		session.close();
		HibernateUtil.shutdown();
	}
}
