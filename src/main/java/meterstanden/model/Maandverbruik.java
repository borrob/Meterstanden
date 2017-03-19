package main.java.meterstanden.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "MAANDVERBRUIK_EXTRA", schema = "APP")
public class Maandverbruik implements Serializable{
	
	//-----------// PRIVATE VARIABLES //-----------//

	private static final long serialVersionUID = -6608899500040103404L;

	@Id
	@Column(name="ID", columnDefinition = "integer")
	private Long id;
	
	@Id
	@Column(name="JAAR")
	private int jaar;
	
	@Id
	@Column(name="MAAND")
	private int maand;
	
	@Id
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="ID_METERSOORT", referencedColumnName="id")
	private Metersoorten metersoort;
	
	@Column(name="VERBRUIK")
	private double verbruik;
	
	@Column(name="AVERAGE")
	private double average;
	
	@Column(name="DELTA")
	private double delta;
	
	//-----------// CONSTRUCTORS //-----------//
	public Maandverbruik() {
		super();
	}
	
	public Maandverbruik(int jaar, int maand, Metersoorten metersoort, float verbruik) {
		super();
		this.jaar = jaar;
		this.maand = maand;
		this.metersoort = metersoort;
		this.verbruik = verbruik;
	}

	public Maandverbruik(int jaar, int maand, Metersoorten metersoort, float verbruik, float average, float delta) {
		super();
		this.jaar = jaar;
		this.maand = maand;
		this.metersoort = metersoort;
		this.verbruik = verbruik;
		this.average = average;
		this.delta = delta;
	}

	//-----------// GETTERS & SETTERS //-----------//
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public double getVerbruik() {
		return verbruik;
	}

	public void setVerbruik(double verbruik) {
		this.verbruik = verbruik;
	}
	
	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	//-----------// METHODS //-----------//
	
	@Override
	public String toString() {
		return "Maandverbruik [id=" + id + ", jaar=" + jaar + ", maand=" + maand + ", metersoort=" + metersoort
				+ ", verbruik=" + verbruik + ", average=" + average + ", delta=" + delta + "]";
	}

}
