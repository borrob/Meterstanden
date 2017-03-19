package main.java.meterstanden.model;


import java.util.Date;

import javax.persistence.*;

/**
 * @author rob
 *
 */
@Entity
@Table(name = "METERSTANDEN", schema = "APP")
public class Meterstanden {

	//-----------// PRIVATE VARIABLES //-----------//
	/**
	 * The ID of the table row.
	 * 
	 * This ID is generate by the database. It has the primary key and therefore should be unique.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	
	/**
	 * Date of the measurement of the meterstand
	 */
	@Temporal(TemporalType.DATE)
	@Column(name="DATUM")
	private Date datum;
	
	/**
	 * The actual meterstand
	 */
	@Column(name="WAARDE")
	private double waarde;
	
	/**
	 * Extra description of this meterstand
	 */
	@Column(name="OMSCHRIJVING")
	private String omschrijving;
	
	/**
	 * Metersoort of this meterstand
	 */
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="ID_METERSOORT", referencedColumnName="id")
	private Metersoorten metersoort;

	//-----------// CONSTRUCTORS //-----------//
	public Meterstanden(){
		super();
	}
	
	public Meterstanden(Date datum, float waarde, String omschrijving, Metersoorten ms){
		this.datum=datum;
		this.waarde=waarde;
		this.omschrijving=omschrijving;
		this.metersoort=ms;
	}
	
	//-----------// GETTERS & SETTERS //-----------//
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public double getWaarde() {
		return waarde;
	}

	public void setWaarde(float waarde) {
		this.waarde = waarde;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public Metersoorten getMetersoort() {
		return metersoort;
	}

	public void setMetersoort(Metersoorten metersoort) {
		this.metersoort = metersoort;
	}

	//-----------// METHODS //-----------//
	public void copyFrom(Meterstanden m){
		this.datum = m.getDatum();
		this.waarde = m.getWaarde();
		this.omschrijving = m.getOmschrijving();
		this.metersoort = m.getMetersoort();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Meterstanden [id=" + id + ", datum=" + datum + ", waarde=" + waarde + ", omschrijving=" + omschrijving
				+ ", id_metersoort=" + metersoort.toString() + "]";
	}
}