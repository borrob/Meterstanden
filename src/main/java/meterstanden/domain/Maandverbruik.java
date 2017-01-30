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
 * Servlet implementation class Maandverbruik
 */
@WebServlet(
		description = "Get the maandverbruik of a specific year and metersoort.", 
		urlPatterns = { 
				"/Maandverbruik", 
				"/maandverbruik", 
				"/MAANDVERBRUIK"
		})
public class Maandverbruik extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Maandverbruik.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Maandverbruik() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int year;
		Long ms;
		
		if (
				request.getParameterMap().containsKey("year") &&
				request.getParameterMap().containsKey("ms")
		){
			year = Integer.valueOf(request.getParameter("year"));
			ms = Long.valueOf(request.getParameter("ms"));
		} else {
			log.error("Maandverbruik requested, but no parameters were given.");
			throw new ServletException("missing parameters");
		}
		
		List<?> rl = getMaandverbruik(ms, year);
		
		Gson gson = new Gson();
		response.getWriter().append(gson.toJson(rl));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**************************************************************************
	 * PRIVATE METHODS
	**************************************************************************/
	
	private List<?> getMaandverbruik(Long ms, int year){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Metersoorten metersoort = session.get(Metersoorten.class, ms);
		
		StringBuilder hql = new StringBuilder();
		hql.append("from Maandverbruik m");
		hql.append(" where m.metersoort = :myMeter");
		hql.append(" and m.jaar = :myYear");
		hql.append(" order by m.maand asc");
		
		Query q = session.createQuery(hql.toString());
		q.setParameter("myMeter", metersoort);
		q.setParameter("myYear", year);
		List<?> rl = q.getResultList();
	
		session.close();
		
		return rl;
	}

}
