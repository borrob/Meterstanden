package main.java.meterstanden.domain;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import main.java.meterstanden.hibernate.HibernateUtil;
import main.java.meterstanden.model.Maandverbruik;
import main.java.meterstanden.model.Metersoorten;

/**
 * Servlet implementation class ShowMeterverbruik
 */
@WebServlet("/ShowMeterverbruikgraph")
public class ShowMeterverbruikGraph extends HttpServlet { // NO_UCD (unused code)
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowMeterverbruikGraph() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		Metersoorten ms = session.get(Metersoorten.class, 2L); //keeping it simple for now
		
		StringBuilder hql = new StringBuilder();
		hql.append("from Maandverbruik mv");
		hql.append(" where mv.metersoort = :myMs");
		hql.append(" and mv.jaar = :myJaar"); //keeping it simple for now
		Query qMaandverbruiken = session.createQuery(hql.toString());
		qMaandverbruiken.setParameter("myMs", ms);
		qMaandverbruiken.setParameter("myJaar", 2016);
		
		List<?> maandverbruikenList = qMaandverbruiken.getResultList();
		Iterator<?> maandverbruikenIt = maandverbruikenList.iterator();
		
		session.close();
		
		Float[] monthUsages = new Float[12];
		
		while(maandverbruikenIt.hasNext()){
			Maandverbruik mv = (Maandverbruik) maandverbruikenIt.next();
			monthUsages[mv.getMaand()] = mv.getVerbruik();
		}
		
		StringBuilder monthOverviewStringBuilder = new StringBuilder();
		monthOverviewStringBuilder.append("[");
		for (int i=0; i<12; i++){
			monthOverviewStringBuilder.append(String.valueOf(monthUsages[i]));
			if(i<12-1){
				monthOverviewStringBuilder.append(", ");
			} else {
				monthOverviewStringBuilder.append("]");
			}
		}

		request.setAttribute("monthoverview", monthOverviewStringBuilder.toString());
		request.setAttribute("legend", "2016 - water");

		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/ShowMeterverbruikGraph.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}