package main.java.meterstanden.domain;

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import main.java.meterstanden.hibernate.HibernateUtil;
import main.java.meterstanden.model.Metersoorten;

/**
 * Servlet implementation class ShowMeterstanden
 */
@WebServlet("/ShowMetersoorten")
public class ShowMetersoorten extends HttpServlet { // NO_UCD (unused code)
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ShowMetersoorten.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowMetersoorten() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("theMetersoorten", getMetersoorten());

		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/ShowMetersoorten.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	@SuppressWarnings("unchecked")
	private List<Metersoorten> getMetersoorten(){
		log.debug("Getting all Metersoorten");
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Query query = session.createQuery("from Metersoorten");
		List<?> rl = query.getResultList();
		
		session.close();

		return (List<Metersoorten>) rl;
	}

}
