package main.java.meterstanden.model;

import javax.persistence.*;

/**
 * The mapping of table "metersoorten" to java object.
 * 
 * @author rob
 *
 */
@Entity
@Table(name = "METERSOORTEN", schema = "APP")
public class Metersoorten {

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
	 * Descriptive text of the meter.
	 */
	@Column(name="METERSOORT")
	private String metersoort;
	
	/**
	 * The unit of this meter.
	 */
	@Column(name="UNIT")
	private String unit;
	
	//-----------// CONSTRUCTORS //-----------//
	public Metersoorten(){
		super();
	}
	
	public Metersoorten(String ms, String un){
		super();
		this.metersoort=ms;
		this.unit=un;
	}

	//-----------// GETTER AND SETTERS //-----------//
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMetersoort() {
		return metersoort;
	}

	public void setMetersoort(String metersoort) {
		this.metersoort = metersoort;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	/* METHODS */

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Metersoorten [id=" + id + ", metersoort=" + metersoort + ", unit=" + unit + "]";
	}
	
	public void copyFrom(Metersoorten m){
		this.metersoort=m.getMetersoort();
		this.unit=m.getUnit();
	}
}
