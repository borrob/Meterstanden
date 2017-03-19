package main.java.meterstanden.model;

import javax.persistence.*;

@Entity
@Table(name = "MAANDVERBRUIK", schema = "APP")
public class MaandverbruikTab {

	//-----------// PRIVATE VARIABLES //-----------//
	/**
	 * The ID of the table row.
	 * 
	 * This ID is generate by the database. It has the primary key and therefore should be unique.
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", columnDefinition = "integer")
	private Long id;
	
	@Column(name="JAAR")
	private int jaar;
	
	@Column(name="MAAND")
	private int maand;
	
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="ID_METERSOORT", referencedColumnName="ID")
	private Metersoorten metersoort;
	
	@Column(name="VERBRUIK")
	private double verbruik;
		
	//-----------// CONSTRUCTORS //-----------//
	public MaandverbruikTab() {
		super();
	}
	
	public MaandverbruikTab(int jaar, int maand, Metersoorten metersoort, double verbruik) {
		super();
		this.jaar = jaar;
		this.maand = maand;
		this.metersoort = metersoort;
		this.verbruik = verbruik;
	}

	public MaandverbruikTab(int jaar, int maand, Metersoorten metersoort, double verbruik, double average, double delta) {
		super();
		this.jaar = jaar;
		this.maand = maand;
		this.metersoort = metersoort;
		this.verbruik = verbruik;
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

	public void setVerbruik(float verbruik) {
		this.verbruik = verbruik;
	}
	
	//-----------// METHODS //-----------//
	
	@Override
	public String toString() {
		return "Maandverbruik [id=" + id + ", jaar=" + jaar + ", maand=" + maand + ", metersoort=" + metersoort
				+ ", verbruik=" + verbruik + "]";
	}

}
