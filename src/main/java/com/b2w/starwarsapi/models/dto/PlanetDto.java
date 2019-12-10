package com.b2w.starwarsapi.models.dto;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import com.b2w.starwarsapi.models.Planet;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@JsonInclude(Include.NON_NULL)
public class PlanetDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	// Properties:
	
	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;
	
	private String name;
	
	private String climate;
	
	private String terrain;
	
	private Set<String> films = new LinkedHashSet<>();
	
	
	// Constructors:
	public PlanetDto() {
	
	}
	
	public PlanetDto(Planet planet) {
		this.setId(planet.getId())
			.setName(planet.getName())
			.setClimate(planet.getClimate())
			.setTerrain(planet.getTerrain())
			.setFilms(planet.getFilms());
	}
	
	
	// Getters and Setters:
	
	public ObjectId getId() {
		return id;
	}
	
	public PlanetDto setId(ObjectId id) {
		this.id = id;
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public PlanetDto setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getClimate() {
		return climate;
	}
	
	public PlanetDto setClimate(String climate) {
		this.climate = climate;
		return this;
	}
	
	public String getTerrain() {
		return terrain;
	}
	
	public PlanetDto setTerrain(String terrain) {
		this.terrain = terrain;
		return this;
	}
	
	public Set<String> getFilms() {
		return films;
	}
	
	public PlanetDto setFilms(Set<String> films) {
		this.films = films;
		return this;
	}
	
	// End Getters and Setters:
	
	
	public static List<PlanetDto> convert(List<Planet> planets) {
		return planets.stream()
				.map(PlanetDto::of)
				.collect(Collectors.toList());
	}

	public static PlanetDto of(Planet planet) {
		return new PlanetDto(planet);
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((climate == null) ? 0 : climate.hashCode());
		result = prime * result + ((films == null) ? 0 : films.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((terrain == null) ? 0 : terrain.hashCode());
		return result;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlanetDto other = (PlanetDto) obj;
		if (climate == null) {
			if (other.climate != null)
				return false;
		} else if (!climate.equals(other.climate))
			return false;
		if (films == null) {
			if (other.films != null)
				return false;
		} else if (!films.equals(other.films))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (terrain == null) {
			if (other.terrain != null)
				return false;
		} else if (!terrain.equals(other.terrain))
			return false;
		return true;
	}
	
}
