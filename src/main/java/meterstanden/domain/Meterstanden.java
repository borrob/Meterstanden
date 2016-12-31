package main.java.meterstanden.domain;

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
 * Servlet implementation class Meterstanden
 */
@WebServlet(
		description = "REST interface for meterstanden.", 
		urlPatterns = { 
				"/Meterstanden", 
				"/meterstanden", 
				"/METERSTANDEN"
		})
public class Meterstanden extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Meterstanden.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Meterstanden() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		List<Meterstanden> myMeterstanden = getLastMeterstanden("0");
		
		Gson gson = new Gson();
		log.debug("The meterstanden JSON:");
		log.debug(gson.toJson(myMeterstanden));
		response.getWriter().append(gson.toJson(myMeterstanden));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
