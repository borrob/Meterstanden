package main.java.meterstanden.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

/**
 * Servlet implementation class Util
 */
@WebServlet({ "/Util", "/util", "/UTIL" })
public class Util extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Util.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Util() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("q").equalsIgnoreCase("maandverbruikjaar"))
		{
			log.debug("getting maandverbruik_jaar");
			List<?> rl = getDistinctJaren();
			List<HashMap<String, Integer>> map = maanverbruikJaarToHashMap(rl);
			
			Gson gson = new Gson();
			response.getWriter().append(gson.toJson(map));
		} else {
			response.getWriter().append("Missing paramters.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**************************************************************************
	 * PRIVATE METHODS
	**************************************************************************/
	
	private List<?> getDistinctJaren(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuffer hql = new StringBuffer();
		hql.append("select distinct m.jaar as jaar from Maandverbruik m");
		hql.append(" order by m.jaar desc");
		
		Query q = session.createQuery(hql.toString());
		List<?> rl = q.getResultList();
		session.close();
		return rl;
	}
	
	private List<HashMap<String, Integer>> maanverbruikJaarToHashMap(List<?> rl){
		List<HashMap<String, Integer>> map = new ArrayList<HashMap<String, Integer>>();
		
		Iterator<?> rl_i = rl.iterator();
		while (rl_i.hasNext()){
			int jaar = (Integer)rl_i.next();
			HashMap<String, Integer> hm = new HashMap<String, Integer>();
			hm.put("jaar", jaar);
			map.add(hm);
		}
		
		return map;
	}

}
