package meterstanden.model;


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
	private Long id;
	
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
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="id_metersoort", referencedColumnName="id")
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
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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