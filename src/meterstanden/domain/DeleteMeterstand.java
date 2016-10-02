package meterstanden.domain;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import meterstanden.hibernate.HibernateUtil;

/**
 * Servlet implementation class DeleteMeterstand
 */
@WebServlet("/DeleteMeterstand")
public class DeleteMeterstand extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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

		if (HibernateUtil.deleteMeterstand(id)){
			message = "De meterstand is verwijderd.";
		} else {
			message = "Het verwijderen van de meterstand is niet gelukt.";
		}
		
		//Redirect to show meterstanden
		request.setAttribute("message", message);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/ShowMeterstanden");
		rd.forward(request, response);
	}
}
