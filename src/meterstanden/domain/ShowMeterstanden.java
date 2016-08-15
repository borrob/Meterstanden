package meterstanden.domain;

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

import meterstanden.hibernate.HibernateUtil;
import meterstanden.model.Meterstanden;
import meterstanden.model.Metersoorten;

/**
 * Servlet implementation class ShowMeterstanden
 */
@WebServlet("/ShowMeterstanden")
public class ShowMeterstanden extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ShowMeterstanden.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowMeterstanden() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "";
		String selectie;
		if (request.getParameterMap().containsKey("selection")){
			selectie = request.getParameter("selection");
		} else {
			selectie = "0";
		}
		
		request.setAttribute("theMeterstanden", getLastMeterstanden(selectie));
		request.setAttribute("theMetersoorten", getMetersoorten());
		request.setAttribute("selectedMetersoort", selectie);

		if (message.length()>0){message=message.substring(0, message.length()-"<br/>".length());}
		String message_org = (String)request.getAttribute("message");
		if (message_org != null){message += message_org + "<br/>" + message;}
		request.setAttribute("message", message);

		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/ShowMeterstanden.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	@SuppressWarnings("unchecked")
	private List<Meterstanden> getLastMeterstanden(String selectie){
		//TODO write javadoc
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
		query.setMaxResults(20); //todo this 20 should be configured with a config file.
		List<?> rl = query.getResultList();
		
		session.close();

		return (List<Meterstanden>) rl;
		
	}
	
	@SuppressWarnings("unchecked")
	private List<Metersoorten> getMetersoorten(){
		//TODO write javadoc
		log.debug("Getting all Metersoorten");
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Query query = session.createQuery("from Metersoorten");
		List<?> rl = query.getResultList();
		
		session.close();

		return (List<Metersoorten>) rl;
		
	}

}
