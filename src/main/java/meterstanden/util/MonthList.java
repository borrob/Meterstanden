package main.java.meterstanden.util;

import java.util.List;

import main.java.meterstanden.model.Maandverbruik;

public class MonthList {
	private int jaar;
	private int maand;
	private List<Maandverbruik> mv;
	
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
}
