package main.java.meterstanden.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "JAARVERBRUIK", schema = "APP")
public class Jaarverbruik implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2711735179826916347L;

	//private static final long serialVersionUID = 1L;
	
	//-----------// PRIVATE VARIABLES //-----------//
	@Id
	@Column(name="ID")
	private int id;
	
	@Id
	@Column(name="MS")
	private String ms;
	
	@Id
	@Column(name="JAAR")
	private int jaar;
	
	@Column(name="SOM")
	private double som;
	
	//-----------// CONSTRUCTORS //-----------//
	public Jaarverbruik() {
		super();
	}
	
	//-----------// GETTERS & SETTERS //-----------//

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMs() {
		return ms;
	}

	public void setMs(String metersoort) {
		this.ms = metersoort;
	}

	public int getJaar() {
		return jaar;
	}

	public void setJaar(int jaar) {
		this.jaar = jaar;
	}

	public double getSom() {
		return som;
	}

	public void setSom(float som) {
		this.som = som;
	}

	//-----------// METHODS //-----------//
	@Override
	public String toString() {
		return "Jaarverbruik [id=" + id + ", metersoort=" + ms + ", jaar=" + jaar + ", som=" + som + "]";
	}
}
