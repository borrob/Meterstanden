package meterstanden.model;


import java.util.Date;

import javax.persistence.*;

/**
 * @author rob
 * @version 0.1
 * @since 0.1
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
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * Date of the measurement of the meterstand
	 */
	@Temporal(TemporalType.DATE)
	private Date datum;
	
	/**
	 * The actual meterstand
	 */
	private float waarde;
	
	/**
	 * Extra description of this meterstand
	 */
	private String omschrijving;
	
	/**
	 * Metersoort of this meterstand
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_metersoort", referencedColumnName="id")
	private Metersoorten metersoort;

	//-----------// GETTERS & SETTERS //-----------//
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public float getWaarde() {
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Meterstanden [id=" + id + ", datum=" + datum + ", waarde=" + waarde + ", omschrijving=" + omschrijving
				+ ", id_metersoort=" + metersoort.toString() + "]";
	}
}