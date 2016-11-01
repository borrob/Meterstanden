package main.java.meterstanden.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import main.java.meterstanden.model.Maandverbruik;
import main.java.meterstanden.model.Metersoorten;
import main.java.meterstanden.model.Meterstanden;

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
			config.configure("/hibernate.cfg.xml");
			config.addAnnotatedClass(Metersoorten.class);
			config.addAnnotatedClass(Meterstanden.class);
			config.addAnnotatedClass(Maandverbruik.class);
			
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
	 * @return true if success, false if failure.
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
	
	/**
	 * Delete an entry of meterstand from the database.
	 * 
	 * @param id the id of the meterstand to delete.
	 * @return true if success, false if failure.
	 */
	public static boolean deleteMeterstand(Long id){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Meterstanden ms = session.get(Meterstanden.class, id);
			session.delete("Meterstanden", ms);
			session.getTransaction().commit();
			log.debug("Meterstand with id = " + Long.valueOf(id) + " is deleted.");
		} catch (Exception e) {
			log.error("Could not delete meterstand. Got error: " + e.toString() + " for meterstand: " + Long.valueOf(id));
			return false;
		} finally {
			session.close();
		}
		return true;
	}
	
	/**
	 * Persist an object of Maandverbruik
	 * 
	 * @param mv the Maandverbruik to save in the database
	 * @return true if succes, false if failure
	 */
	public static boolean persistMaandverbruik(Maandverbruik mv){
		//TODO: javadoc
		Session session = getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.save(mv);
			session.getTransaction().commit();
			log.info("New maandverbruik added: " + mv.toString());
		} catch (Exception e) {
			log.error("Could not save maandverbruik, got error: " + e.toString());
			return false;
		} finally {
			session.close();
		}
		return true;
	}
	
	/**
	 * Delete the Maandverbruik with id from the database
	 *  
	 * @param id the id to delete
	 * @return true if succes, false if failure
	 */
	public static boolean deleteMaandverbruik(Long id){
		//TODO: javadoc
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Maandverbruik mv = session.get(Maandverbruik.class, id);
			session.delete("Maandverbruik", mv);
			session.getTransaction().commit();
			log.debug("Maandverbruik with id = " + Long.valueOf(id) + " is deleted.");
		} catch (Exception e) {
			log.error("Could not delete maandverbruik. Got error: " + e.toString());
			return false;
		} finally {
			session.close();
		}
		return true;
	}
}
