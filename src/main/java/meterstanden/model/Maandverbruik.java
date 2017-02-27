package main.java.meterstanden.model;

import javax.persistence.*;

@Entity
@Table(name = "MAANDVERBRUIK_EXTRA", schema = "APP")
public class Maandverbruik {
	
	//-----------// PRIVATE VARIABLES //-----------//
	/**
	 * The ID of the table row.
	 * 
	 * This ID is generate by the database. It has the primary key and therefore should be unique.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private int jaar;
	
	private int maand;
	
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="id_metersoort", referencedColumnName="id")
	private Metersoorten metersoort;
	
	private float verbruik;
	
	private float average;
	
	private float delta;
	
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

	public float getVerbruik() {
		return verbruik;
	}

	public void setVerbruik(float verbruik) {
		this.verbruik = verbruik;
	}
	
	public float getAverage() {
		return average;
	}

	public void setAverage(float average) {
		this.average = average;
	}

	public float getDelta() {
		return delta;
	}

	public void setDelta(float delta) {
		this.delta = delta;
	}

	//-----------// METHODS //-----------//
	
	@Override
	public String toString() {
		return "Maandverbruik [id=" + id + ", jaar=" + jaar + ", maand=" + maand + ", metersoort=" + metersoort
				+ ", verbruik=" + verbruik + ", average=" + average + ", delta=" + delta + "]";
	}

}
