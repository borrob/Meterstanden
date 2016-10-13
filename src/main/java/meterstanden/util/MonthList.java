package main.java.meterstanden.util;

import java.util.List;

import main.java.meterstanden.model.Meterverbruik;

public class MonthList {
	private int jaar;
	private int maand;
	private List<Meterverbruik> mv;
	
	public MonthList() {
		super();
	}
	
	public MonthList(int jaar, int maand, List<Meterverbruik> mv) {
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
	public List<Meterverbruik> getMv() {
		return mv;
	}
	public void setMv(List<Meterverbruik> mv) {
		this.mv = mv;
	}

	@Override
	public String toString() {
		return "MonthList [jaar=" + jaar + ", maand=" + maand + ", mv=" + mv + "]";
	}
}
