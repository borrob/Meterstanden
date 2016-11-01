package main.java.meterstanden.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import main.java.meterstanden.hibernate.HibernateUtil;
import main.java.meterstanden.model.Maandverbruik;
import main.java.meterstanden.model.Metersoorten;

public class MonthList {
	private int jaar;
	private int maand;
	private List<Maandverbruik> mv;
	private static Logger log = Logger.getLogger(MonthList.class);
	
	public MonthList() {
		super();
	}
	
	public MonthList(int jaar, int maand, List<Maandverbruik> mv) {
		super();
		this.jaar = jaar;
		this.maand = maand;
		this.mv = mv;
	}
	public int getJaar() {
		return jaar;
	}
	public void setJaar(int jaar) {
		this.jaar = jaar;
	}
	public int getMaand() {
		return maand;
	}
	public void setMaand(int maand) {
		this.maand = maand;
	}
	public List<Maandverbruik> getMv() {
		return mv;
	}
	public void setMv(List<Maandverbruik> mv) {
		this.mv = mv;
	}

	@Override
	public String toString() {
		return "MonthList [jaar=" + jaar + ", maand=" + maand + ", mv=" + mv + "]";
	}
	
	public static MonthList fillMonth(int y, int m){
		MonthList result = new MonthList();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Query q_ms = session.createQuery("from Metersoorten m order by m.id");
		List<?> rl_ms = q_ms.getResultList();
		Iterator<?> it_ms = rl_ms.listIterator();
		
		List<Maandverbruik> mv_list = new ArrayList<Maandverbruik>();
		
		while (it_ms.hasNext()){
			Metersoorten ms = (Metersoorten)it_ms.next();
			StringBuilder hql = new StringBuilder(128);
			hql.append("from Maandverbruik m");
			hql.append(" where m.metersoort = :myMeter");
			hql.append(" and m.jaar = :myJaar");
			hql.append(" and m.maand = :myMaand");
			Maandverbruik mv = null;
			try {
				Query q_mv = session.createQuery(hql.toString());
				q_mv.setParameter("myMeter", ms);
				q_mv.setParameter("myJaar", y);
				q_mv.setParameter("myMaand", m);
				mv = (Maandverbruik) q_mv.getSingleResult();
			} catch (NoResultException mvCanBeNull) {
				log.debug("There was no maandverbuik for " + Integer.toString(y) + "-" + Integer.toString(m) + ", meter: " + ms.toString() + ".");
			}
			mv_list.add(mv);
		}
		
		session.close();
		
		result.setJaar(y);
		result.setMaand(m);
		result.setMv(mv_list);
		
		return result;
	}
}
