package com.b2w.starwarsapi.models;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.bson.types.ObjectId;


/**
 * Entity model to represent a planet from Mongo Database
 * 
 * @author Marcus Santos
 *
 */
public class Planet implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Properties:
	private ObjectId id;
	private String name;
	private String climate;
	private String terrain;
	private Date created = new Date();
	private Set<String> films = new LinkedHashSet<>();

	
	// Constructors:
	public Planet() {
		
	}
	
	public Planet(String name, String climate, String terrain) {
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
	}

	
	// Getters and Setters:
	
	public ObjectId getId() {
		return id;
	}
	
	public String toObjectIdString() {
		return id.toString();
	}
	
	public Planet setId(ObjectId id) {
		this.id = id;
		return this;
	}
	
	
	public String getName() {
		return name;
	}

	public Planet setName(String name) {
		this.name = name;
		return this;
	}

	public String getClimate() {
		return climate;
	}

	public Planet setClimate(String climate) {
		this.climate = climate;
		return this;
	}

	public String getTerrain() {
		return terrain;
	}

	public Planet setTerrain(String terrain) {
		this.terrain = terrain;
		return this;
	}

	public Date getCreated() {
		return created;
	}
	
	public Set<String> getFilms() {
		return films;
	}
	
	public Planet setFilms(Set<String> films) {
		this.films = films;
		return this;
	}

	
	// End Getters and Setters:
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Planet other = (Planet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

	@Override
	public String toString() {
		return "Planet [id=" + id + ", name=" + name + ", climate=" + climate + ", terrain=" + terrain + ", created="
				+ created + ", films=" + films + "]";
	}
	
	
}
