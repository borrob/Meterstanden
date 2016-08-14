package meterstanden.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import meterstanden.model.Metersoorten;
import meterstanden.model.Meterstanden;

/**
 * @author rob
 */
public class HibernateUtil {

	//-----------// PRIVATE VARIABLES //-----------//
	/**
	 * The sessionfactory to get sessions to the database.
	 */
	private static SessionFactory sf;	
	
	/**
	 * Serviceregistry to create a sessionfactory.
	 */
	private static ServiceRegistry sr;
	
	/**
	 * The logger to write logs.
	 */
	private static Logger log = Logger.getLogger(HibernateUtil.class);

	//-----------// METHODS //-----------//	
	/**
	 * Obtain the sessionfactory (create if it doesn't exist yet).
	 * 
	 * @return the sessionfactory
	 */
	public static SessionFactory getSessionFactory(){
		try {
			if (sf != null){
				return sf;
			}
			
			log.debug("Creating a new SessionFactory.");
			Configuration config = new Configuration();
			config.configure("hibernate.cfg.xml");
			config.addAnnotatedClass(Metersoorten.class);
			config.addAnnotatedClass(Meterstanden.class);
			
			sr = new StandardServiceRegistryBuilder().applySettings(
		            config.getProperties()).build();
		    sf = config.buildSessionFactory(sr);
		    
		    return sf;
		} catch (Throwable except){
			log.fatal("Something went wrong with the hibernate configuration.");
			throw new ExceptionInInitializerError(except);
		}
	}
	
	/**
	 * Shutdown the sessionfactory.
	 */
	public static void shutdown(){
		getSessionFactory().close();
	}

	/**
	 * Persists an object of Meterstanden into the database.
	 * 
	 * @param ms the Meterstanden object to add
	 * @return true if succes, false if no succes
	 */
	public static boolean persistMeterstand(Meterstanden ms){
		Session session = getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.save(ms);
			session.getTransaction().commit();
			log.info("New meterstand added.");
		} catch (Exception e) {
			log.error("Could not save meterstand, got error: " + e.toString() + "for meterstand: " + ms.toString());
			return false;
		} finally {
			session.close();
		}
		return true;
	}
}
