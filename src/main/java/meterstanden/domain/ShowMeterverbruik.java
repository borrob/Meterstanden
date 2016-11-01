package main.java.meterstanden.domain;

import java.io.IOException;
import java.util.ArrayList;
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
import main.java.meterstanden.util.MonthList;

/**
 * Servlet implementation class ShowMeterverbruik
 */
@WebServlet("/ShowMeterverbruik")
public class ShowMeterverbruik extends HttpServlet { // NO_UCD (unused code)
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowMeterverbruik() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<MonthList> ml_overviewList = new ArrayList<MonthList>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();	
		
		Query query = session.createQuery("from Metersoorten");
		List<?> metersoorten = query.getResultList();
		
		StringBuilder hql = new StringBuilder();
		hql.append("select distinct mv.maand, mv.jaar");
		hql.append(" from Maandverbruik mv");
		hql.append(" order by mv.jaar desc, mv.maand desc");
		Query qMaandverbruiken = session.createQuery(hql.toString());
		qMaandverbruiken.setMaxResults(20);
		List<?> maandverbruikenList = qMaandverbruiken.getResultList();
		Iterator<?> maandverbruikenIt = maandverbruikenList.iterator();
		
		session.close();
		
		while (maandverbruikenIt.hasNext()){
			Object[] mj = (Object[]) maandverbruikenIt.next();
			MonthList ml_overview = MonthList.fillMonth((int)mj[1], (int)mj[0]);
			ml_overviewList.add(ml_overview);
		}
		
		request.setAttribute("theMeterverbruik", ml_overviewList);
		request.setAttribute("theMetersoorten", metersoorten);

		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/ShowMeterverbruik.jsp");
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