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

/**
 * Servlet implementation class JaaroverzichtDomain
 */
@WebServlet(
		description = "Get the yearly overview",
		urlPatterns = {
				"/Jaaroverzicht",
				"/JAAROVERZICHT",
				"/jaaroverzicht"
		}
	)

public class JaaroverzichtDomain extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(JaaroverzichtDomain.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JaaroverzichtDomain() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("Getting the jaarverbruik.");
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuilder hql = new StringBuilder();
		hql.append("from Jaarverbruik");
		Query q = session.createQuery(hql.toString());
		List<?> rl = q.getResultList();
		
		Gson gson = new Gson();
		response.getWriter().append(gson.toJson(rl));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
