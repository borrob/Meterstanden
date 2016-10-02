package meterstanden.domain;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import meterstanden.hibernate.HibernateUtil;
import meterstanden.model.Metersoorten;
import meterstanden.model.Meterstanden;

/**
 * Servlet implementation class NewMeterstand
 */
@WebServlet("/newMeterstand")
public class NewMeterstand extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(NewMeterstand.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewMeterstand() {
        super();
    }
    
    /**
     * A GET-request does not make sense --> redirect to show meterstanden
     * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/ShowMeterstanden");
		rd.forward(request, response);
	}
	
	/**
	 * Add new meterstand to the database
	 * 
	 * Checks if the POST-request has the required parameters and that they are valid. Creates a new meterstanden object
	 * and persists it into the database. Notifies the user. Redirects to the referring page.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String message;
		
		if (checkParameterExistance(request)){	
			
			Date datum = parseDatum(request.getParameter("deDatum"));
			Metersoorten meter = parseMetersoort(request.getParameter("deMeter"));
			String opmerking = request.getParameter("deOpmerking");
			float waarde = Float.parseFloat(request.getParameter("deStand"));
		
			if(checkParameters(datum, meter, opmerking, waarde)){
			
				Meterstanden newMeterstand = new Meterstanden(datum, waarde, opmerking, meter);
				
				if (HibernateUtil.persistMeterstand(newMeterstand)){
					message = "De nieuwe meterstand is toegevoegd.<br/>";
				} else {
					//error with persisingMeterstand
					log.error("Could not save meterstand: " + newMeterstand.toString());
					message = "Het is niet gelukt om de nieuwe meterstand toe te voegen.<br/>";
				}
			} else {
				//parameters not ok
				message = "De invoerparameters waren niet OK.<br/>";
			} 
		} else {
			//parameter do not exist
			message = "De invoerparameters waren niet volledig.<br/>";
		}

		request.setAttribute("message", message);
		//Redirect to show the last meterstanden
		//TODO: set selection of metersoort
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/ShowMeterstanden");
		rd.forward(request, response);
	}
	
	/**
	 * Check if the required parameters to add a new meterstand to the database are present in the POST-request.
	 * 
	 * @param request the http request
	 * @return true if the required parameters are present, false if they are not
	 */
	private boolean checkParameterExistance(HttpServletRequest request){
		boolean testExistance = (request.getParameterMap().containsKey("deDatum") &&
									request.getParameterMap().containsKey("deMeter") &&
									request.getParameterMap().containsKey("deStand") &&
									request.getParameterMap().containsKey("deOpmerking")
								);
		if (testExistance && request.getParameter("deDatum").isEmpty()){testExistance=false;log.error("Datum is null");}
		if (testExistance && request.getParameter("deMeter").isEmpty()){testExistance=false;log.error("Meter is null");}
		if (testExistance && request.getParameter("deStand").isEmpty()){testExistance=false;log.error("Stand is null");}
		//Opmerking: null is allowed
		//if (testExistance && request.getParameter("deOpmerking").isEmpty()){testExistance=false;log.error("Opmerkingis null");}
		return testExistance;
	}
	
	
	private boolean checkParameters(Date datum, Metersoorten meter, String opmerking, float waarde){
		boolean ok = true;
		
		//Check datum
		//TODO: move away from java.util.Date and use java.time.*
		if (ok && datum.after(new Date())){ok = false;log.error("Date after today");}
		Calendar cal = Calendar.getInstance();
		cal.set(1900, 1,1);
		if (ok && datum.before(cal.getTime())){ok=false;log.error("Date is too early");}
		
		//Check meter
		if (ok){
			try{
				Query query = HibernateUtil.getSessionFactory().openSession().createQuery("select 1 from Metersoorten m where m.id = :key");
				query.setParameter("key", meter.getId());
				query.getSingleResult();
			} catch (NoResultException e) {
				log.error("meter not found");
				ok = false;
			}
		}
		
		//Check opmerking
		if (ok && opmerking.length()>255){ok=false;log.error("Opmerking too long.");}
		
		//Check waarde
		// ? can't think of something useful to check
		
		return ok;
	}
	
	private Date parseDatum(String datumstring){
		//TODO: javadoc
		
		Date datum = null;
		SimpleDateFormat sdf = new SimpleDateFormat ("dd-MM-yyyy");
		
		try {
			datum = sdf.parse(datumstring);
		} catch (ParseException e) {
			log.error("Dateformat wrong - error: " + e.toString());
			// TODO notify user
		}
		return datum;
	}
	
	
	private Metersoorten parseMetersoort(String meter){
		//TODO: javadoc
		Long meterid = Long.parseLong(meter);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Metersoorten metersoort = session.get(Metersoorten.class, meterid);
		session.close();
		return metersoort;
	}

}
