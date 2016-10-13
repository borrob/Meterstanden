package main.java.meterstanden.model;

public class Meterverbruik {
	
	private int jaar;
	private int maand;
	private Metersoorten metersoort;
	private float waarde;
	
	public Meterverbruik() {
		super();
	}
	
	public Meterverbruik(int jaar, int maand, Metersoorten metersoort, float waarde) {
		super();
		this.jaar = jaar;
		this.maand = maand;
		this.metersoort = metersoort;
		this.waarde = waarde;
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
	
	public Metersoorten getMetersoort() {
		return metersoort;
	}
	
	public void setMetersoort(Metersoorten metersoort) {
		this.metersoort = metersoort;
	}
	
	public float getWaarde() {
		return waarde;
	}
	
	public void setWaarde(float waarde) {
		this.waarde = waarde;
	}

}
