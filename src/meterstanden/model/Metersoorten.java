package meterstanden.model;

import javax.persistence.*;

/**
 * The mapping of table "metersoorten" to java object.
 * 
 * @author rob
 * @version 0.1
 * @since 0.1
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
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * Descriptive text of the meter.
	 */
	private String metersoort;

	//-----------// GETTER AND SETTERS //-----------//
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
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