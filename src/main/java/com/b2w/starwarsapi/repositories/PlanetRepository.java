package com.b2w.starwarsapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.b2w.starwarsapi.models.Planet;

@Repository
public interface PlanetRepository {
	
	Planet save(Planet planet);
	
	List<Planet> findAll();
	
	List<Planet> findByName(String name);
	
	Optional<Planet> findOne(String id);
	
	long count();

    long delete(String id);

    long deleteAll();

    Planet update(Planet person);
}
