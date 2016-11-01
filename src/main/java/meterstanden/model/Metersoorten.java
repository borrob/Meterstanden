package main.java.meterstanden.model;

import javax.persistence.*;

/**
 * The mapping of table "metersoorten" to java object.
 * 
 * @author rob
 *
 */
@Entity
@Table(name = "metersoorten", schema = "APP")
public class Metersoorten {

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
	 * Descriptive text of the meter.
	 */
	private String metersoort;

	//-----------// GETTER AND SETTERS //-----------//
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMetersoort() {
		return metersoort;
	}

	public void setMetersoort(String metersoort) {
		this.metersoort = metersoort;
	}

	/* METHODS */
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Metersoorten [id=" + id + ", metersoort=" + metersoort + "]";
	}
}
