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
import com.google.gson.GsonBuilder;

import main.java.meterstanden.hibernate.HibernateUtil;
import main.java.meterstanden.model.Metersoorten;
import main.java.meterstanden.model.Meterstanden;
import main.java.meterstanden.util.UpdateVerbruik;



/**
 * Servlet implementation class Meterstanden
 */
@WebServlet(
		description = "REST interface for meterstanden.", 
		urlPatterns = { 
				"/Meterstanden", 
				"/meterstanden", 
				"/METERSTANDEN"
		})
public class MeterstandenDomain extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8705366207131952124L;
	private static final Logger log = Logger.getLogger(MeterstandenDomain.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MeterstandenDomain() {
        super();
    }

	/**
	 * Get the last 20 meterstanden (optional for a metersoort).
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ms = request.getParameterMap().containsKey("ms") ? 
				Integer.valueOf(request.getParameter("ms")) : 
				0;
		int p = request.getParameterMap().containsKey("p") ? 
						Integer.valueOf(request.getParameter("p")) : 
						0;
		List<Meterstanden> myMeterstanden = getLastMeterstanden(Integer.toString(ms), p);
		
		Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
		log.debug("The meterstanden JSON:");
		log.debug(gson.toJson(myMeterstanden));
		response.getWriter().append(gson.toJson(myMeterstanden));
	}

	/**
	 * Update a meterstand,
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Working on a POST request: updating a meterstand.");
		
		//read post data from json
		Meterstanden m = jsonToMeterstanden(request);
		
		//persist in database and update maandverbruik
		if(HibernateUtil.updateMeterstand(m)){
			try {
				UpdateVerbruik.updateMeterverbruik(m);
				//todo: fix update meterverbruik when metersoort changes
			} catch (IndexOutOfBoundsException ignoreException) {
				log.error("Could not determine meterstandverbruik.");
			}
		} else {
			throw new ServletException("Error with updating meterstand.");
		}
	}
	
	/**
	 * Add a new meterstand.
	 * 
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Working on a PUT request: adding meterstand.");
		
		//read post data from json
		Meterstanden m = jsonToMeterstanden(request);
		
		//persist in database and update maandverbruik
		if (HibernateUtil.persistMeterstand(m)){
			try {
				UpdateVerbruik.updateMeterverbruik(m);
			} catch (IndexOutOfBoundsException ignoreException) {
				log.error("Could not determine meterstandverbruik.");
			}
		} else {
			throw new ServletException("Could not add meterstand.");
		}
	}
	
	/**
	 * Delete a meterstand.
	 * 
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer ms;
		if (request.getParameterMap().containsKey("ms")){
			ms = Integer.valueOf(request.getParameter("ms")); 
		} else {
			log.error("Deleting meterstand, but no parameters were given.");
			throw new ServletException("missing parameters");
		}

		try {
			HibernateUtil.deleteMeterstand(ms);
		} catch (Exception e) {
			log.error("Could not delete metersoort. Got error: " + e.toString() + " for metersoort: " + Long.valueOf(ms));
			throw new ServletException("Error on deleting metersoort: " + e.toString());
		}
	}
	
	/**************************************************************************
	 * PRIVATE METHODS
	**************************************************************************/
	
	/**
	 * Get the last 20 Meterstanden.
	 * 
	 * @param selectie ID of metersoort to get the Meterstanden from (0 = all)
	 * @return The list of meterstanden
	 */
	@SuppressWarnings("unchecked")
	private List<Meterstanden> getLastMeterstanden(String selectie, Integer page){
		log.debug("Getting the 20 last Meterstanden");
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Long selectieInt = Long.valueOf(selectie);
		Metersoorten metersoort = null;
		boolean useSelection = selectieInt>0;
		
		String hql = "from Meterstanden m ";
		if (useSelection){
			hql += "where m.metersoort = :metersoort ";
			metersoort = session.get(Metersoorten.class, selectieInt);
		}
		hql += "order by m.datum desc";
		
		Query query = session.createQuery(hql);
		if (useSelection){
			query.setParameter("metersoort", metersoort);
		}
		query.setFirstResult(20 * page);
		query.setMaxResults(20); //todo this 20 should be configured with a config file.
		List<?> rl = query.getResultList();
		
		session.close();

		return (List<Meterstanden>) rl;
		
	}
	
	/**
	 * Convert a JSON to meterstanden object
	 * 
	 * @param request the http-request with the json as parameter
	 * @return the input json as a meterstanden object
	 * @throws ServletException when something went wrong.
	 */
	private Meterstanden jsonToMeterstanden(HttpServletRequest request) throws ServletException{
		Meterstanden out;
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
		    BufferedReader reader = request.getReader();
			while (( line = reader.readLine()) != null)
		    	jb.append(line);
		} catch (Exception e) { 
			log.error("Trying to create meterstand, but get error: " + e.toString());
	  		throw new ServletException("Error with updating meterstand: " + e.toString());
	  	}
		Gson gson = new Gson();
	    out = gson.fromJson(jb.toString(), Meterstanden.class);
		
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    Metersoorten ms = session.get(Metersoorten.class, out.getMetersoort().getId());
	    session.close();
	    
	    out.setMetersoort(ms);
	    
	    if (
	    		out.getId()==null &&
	    		out.getMetersoort()==null &&
	    		out.getDatum()==null && 
	    		out.getMetersoort()==null
	    	){
	    	log.error("Missing data for meterstanden.");
	    	throw new ServletException("Missding data for meterstanden.");
	    }
	    return out;
	}

}
