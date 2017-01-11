package main.java.meterstanden.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.google.gson.Gson;

import main.java.meterstanden.hibernate.HibernateUtil;
import main.java.meterstanden.model.Metersoorten;


/**
 * Servlet implementation class Metersoorten
 */
@WebServlet(
		description = "REST interface for meterstanden.", 
		urlPatterns = { 
				"/Metersoorten", 
				"/metersoorten", 
				"/METERSOORTEN"
		})
public class MetersoortenDomain extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Metersoorten.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MetersoortenDomain() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Metersoorten> myMetersoorten = getMetersoorten("0");
		
		Gson gson = new Gson();
		log.debug("The metersoorten JSON:");
		log.debug(gson.toJson(myMetersoorten));
		response.getWriter().append(gson.toJson(myMetersoorten));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Working on a POST request: updating metersoort.");
		
		//read post data from json
		Metersoorten m = jsonToMetersoorten(request);
		
	    //persisting in database
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    try {
		    Metersoorten mOld = session.get(Metersoorten.class, m.getId());
		    mOld.copyFrom(m);
		    session.beginTransaction();
		    session.update(mOld);
		    session.getTransaction().commit();
	    } catch (Exception e) {
	    	log.error("Trying to update metersoort, but get error: " + e.toString());
	  		throw new ServletException("Error with updating metersoort: " + e.toString());
	    } finally {
	    	session.close();
	    }
	}
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Working on a PUT request: adding metersoort.");
		
		//read post data from json
		Metersoorten m = jsonToMetersoorten(request);
		
	    //persisting in database
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.save(m);
			session.getTransaction().commit();
			log.info("New metersoort added.");
		} catch (Exception e) {
			log.error("Could not save metersoort, got error: " + e.toString());
		} finally {
			session.close();
		}
	}
	
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long ms;
		if (request.getParameterMap().containsKey("ms")){
			ms = Long.valueOf(request.getParameter("ms"));
		} else {
			log.error("Deleting metersoort, but no parameters were given.");
			throw new ServletException("missing parameters");
		}

		try {
			HibernateUtil.deleteMetersoort(ms);
			log.debug("Metersoort with id = " + Long.valueOf(ms) + " is deleted.");
		} catch (Exception e) {
			log.error("Could not delete metersoort. Got error: " + e.toString() + " for metersoort: " + Long.valueOf(ms));
			throw new ServletException("Error on deleting metersoort: " + e.toString());
		} 
	}
	
	/**
	 * Get the Metersoorten.
	 * 
	 * @param selectie ID of metersoort to get the Meterstanden from (0 = all)
	 * @return The list of metersoorten
	 */
	@SuppressWarnings("unchecked")
	private List<Metersoorten> getMetersoorten(String selectie){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Long selectieInt = Long.valueOf(selectie);
		Metersoorten metersoort = null;
		boolean useSelection = selectieInt>0;
		
		String hql = "from Metersoorten m ";
		if (useSelection){
			hql += "where m.id = :metersoort ";
		}
		hql += "order by m.id asc";
		
		Query query = session.createQuery(hql);
		if (useSelection){
			query.setParameter("metersoort", metersoort);
		}

		List<?> rl = query.getResultList();
		
		session.close();

		return (List<Metersoorten>) rl;
		
	}
	
	private Metersoorten jsonToMetersoorten(HttpServletRequest request) throws ServletException{
		Metersoorten out;
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
		    BufferedReader reader = request.getReader();
			while (( line = reader.readLine()) != null)
		    	jb.append(line);
		} catch (Exception e) { 
			log.error("Trying to update metersoort, but get error: " + e.toString());
	  		throw new ServletException("Error with updating metersoort: " + e.toString());
	  	}
		Gson gson = new Gson();
	    out = gson.fromJson(jb.toString(), Metersoorten.class);
		
	    return out;
	}

}
