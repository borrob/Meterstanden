package main.java.meterstanden.model;
import java.io.Serializable;

import javax.persistence.*;

@Entity @IdClass(Jaarverbruik.class)
@Table(name = "JAARVERBRUIK", schema = "APP")
public class Jaarverbruik implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//-----------// PRIVATE VARIABLES //-----------//	
	@Id
	private Long id;
	
	@Id
	private String ms;
	
	private int jaar;
	
	private float som;
	
	//-----------// CONSTRUCTORS //-----------//
	public Jaarverbruik() {
		super();
	}
	
	//-----------// GETTERS & SETTERS //-----------//

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public float getSom() {
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
