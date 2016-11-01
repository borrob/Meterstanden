package main.java.meterstanden.domain;

import java.io.IOException;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import main.java.meterstanden.hibernate.HibernateUtil;
import main.java.meterstanden.model.Meterstanden;
import main.java.meterstanden.util.UpdateVerbruik;

/**
 * Servlet implementation class DeleteMeterstand
 */
@WebServlet("/DeleteMeterstand")
public class DeleteMeterstand extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(DeleteMeterstand.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMeterstand() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String message;
		
		String delId = request.getParameter("id");
		Long id = Long.parseLong(delId.substring(delId.indexOf('_')+1));
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Meterstanden ms = session.get(Meterstanden.class, id);

		if (HibernateUtil.deleteMeterstand(id)){
			message = "De meterstand is verwijderd.";
			try {
				UpdateVerbruik.updateMeterverbruik(ms);
				message += "<br> Het maandverbruik is bijgewerkt.";
			} catch (IndexOutOfBoundsException ignoreException) {
					log.error("Could not determine meterstandverbruik.");
			}
		} else {
			message = "Het verwijderen van de meterstand is niet gelukt.";
		}
		
		//Redirect to show meterstanden
		request.setAttribute("message", message);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/ShowMeterstanden");
		rd.forward(request, response);
	}
}
