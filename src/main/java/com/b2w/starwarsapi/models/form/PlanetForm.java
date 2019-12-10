package com.b2w.starwarsapi.models.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.b2w.starwarsapi.models.Planet;

public class PlanetForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Properties:
	
	@NotNull @NotEmpty
	private String name;
	
	@NotNull @NotEmpty
	private String climate;
	
	@NotNull @NotEmpty
	private String terrain;
	
	
	// Constructors:
	public PlanetForm() {
		
	}
	
	public PlanetForm(String name, String climate, String terrain) {
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
	}
	
	
	// Getters and Setters: 


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}
	
	// End Getters and Setters:


	public Planet convert() {
		return new Planet(name, climate, terrain);
	}
}
