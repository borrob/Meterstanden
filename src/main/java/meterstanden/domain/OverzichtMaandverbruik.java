package main.java.meterstanden.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.Gson;

import main.java.meterstanden.hibernate.HibernateUtil;
import main.java.meterstanden.util.MonthList;

/**
 * Servlet implementation class OverzichtMaandverbruik
 */
@WebServlet(
		description = "Get an overview of all the maandverbruiken.", 
		urlPatterns = { 
				"/OverzichtMaandverbruik", 
				"/overzichtmaandverbruik", 
				"/OVERZICHTMAANDVERBRUIK"
		})
public class OverzichtMaandverbruik extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OverzichtMaandverbruik() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int p = request.getParameterMap().containsKey("p") ? 
				Integer.valueOf(request.getParameter("p")) : 
				0;
		
		List<MonthList> ml_overviewList = new ArrayList<MonthList>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		StringBuilder hql = new StringBuilder();
		hql.append("select distinct mv.maand, mv.jaar");
		hql.append(" from Maandverbruik mv");
		hql.append(" order by mv.jaar desc, mv.maand desc");
		Query qMaandverbruiken = session.createQuery(hql.toString());
		qMaandverbruiken.setMaxResults(20);
		qMaandverbruiken.setFirstResult(20 * p);
		List<?> maandverbruikenList = qMaandverbruiken.getResultList();
		Iterator<?> maandverbruikenIt = maandverbruikenList.iterator();
		
		session.close();
		
		while (maandverbruikenIt.hasNext()){
			Object[] mj = (Object[]) maandverbruikenIt.next();
			MonthList ml_overview = MonthList.fillMonth((int)mj[1], (int)mj[0]);
			ml_overviewList.add(ml_overview);
		}
		
		Gson gson = new Gson();
		response.getWriter().append(gson.toJson(ml_overviewList));
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
