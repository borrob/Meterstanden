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

import org.apache.log4j.Logger;
import org.hibernate.Session;

import main.java.meterstanden.hibernate.HibernateUtil;
import main.java.meterstanden.model.Metersoorten;
import main.java.meterstanden.model.Meterverbruik;
import main.java.meterstanden.util.Month;
import main.java.meterstanden.util.MonthList;

/**
 * Servlet implementation class ShowMeterverbruik
 */
@WebServlet("/ShowMeterverbruik")
public class ShowMeterverbruik extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ShowMeterverbruik.class);
       
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
		
		List<MonthList> ml_overview = new ArrayList<MonthList>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();	
		Query query = session.createQuery("from Metersoorten");
		List<?> metersoorten = query.getResultList();
		session.close();
		Iterator<?> metersoortenit = metersoorten.iterator();
		
		int jaar = 2016;
		int maand = 6;
		List<Meterverbruik> meterverbruikLijst= new ArrayList<Meterverbruik>();
		while (metersoortenit.hasNext()){
			Metersoorten ms = (Metersoorten)metersoortenit.next();
			Meterverbruik mv = new Meterverbruik(jaar, maand, ms, Month.getMonthUsage(maand, jaar, ms));
			meterverbruikLijst.add(mv);
		}
		
		MonthList ml = new MonthList(jaar, maand, meterverbruikLijst);
		ml_overview.add(ml);
		log.debug(ml.toString());
		
		request.setAttribute("theMeterverbruik", ml_overview);
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