package com.b2w.starwarsapi.controllers;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.b2w.starwarsapi.models.Planet;
import com.b2w.starwarsapi.models.dto.PlanetDto;
import com.b2w.starwarsapi.repositories.PlanetRepository;

/**
 * @author Marcus Santos
 */
@CrossOrigin
@RestController
@RequestMapping("/api/planets")
public class PrivatePlanetsController {
	
	@Autowired
	private PlanetRepository planetRepository;
	
	
	@GetMapping
	public List<PlanetDto> list(@RequestParam(required = false) String name) {
		
		// if 'name' parameter was provided, use find by name:
		if( name != null ) {
			return PlanetDto.convert(planetRepository.findByName(name));
		}
		
		return PlanetDto.convert( planetRepository.findAll() );
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPlanet(@PathVariable String id) {
		
		if ( !ObjectId.isValid(id) ) {
			return ResponseEntity.badRequest().body("The id passed is not a valid ObjectId");
		}
		
		Optional<Planet> planetOptional = planetRepository.findOne(id);
		
		if( planetOptional.isPresent() ) {
			return ResponseEntity.ok( new PlanetDto( planetOptional.get() ) );
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The Planet with id " + id + " was not found");
	}
	
	@GetMapping("/count")
	public Long getCount() {
		return planetRepository.count();
	}
	
	
	@GetMapping("/{id}/films")
	public ResponseEntity<?> findPlanetsFilmsByPlanetId(@PathVariable String id) {
		
		if ( !ObjectId.isValid(id) ) {
			return ResponseEntity.badRequest().body("The id passed is not a valid ObjectId");
		}
		
		Optional<Planet> planetOptional = planetRepository.findOne(id);
		
		if( planetOptional.isPresent() ) {
			return ResponseEntity.ok( new PlanetDto( planetOptional.get() ).getFilms() );
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A Planet with id " + id + " was not found");
	}
	
	
	@GetMapping("/{id}/films/count")
	public ResponseEntity<?> getCountPlanetsFilmsByPlanetId(@PathVariable String id) {
		
		if ( !ObjectId.isValid(id) ) {
			return ResponseEntity.badRequest().body("The id passed is not a valid ObjectId");
		}
		
		Optional<Planet> planetOptional = planetRepository.findOne(id);
		
		if( planetOptional.isPresent() ) {
			return ResponseEntity.ok( new PlanetDto( planetOptional.get() ).getFilms().size() );
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A Planet with id " + id + " was not found");
	}

}
