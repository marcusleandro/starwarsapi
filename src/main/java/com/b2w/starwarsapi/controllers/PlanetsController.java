package com.b2w.starwarsapi.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.b2w.starwarsapi.models.Planet;
import com.b2w.starwarsapi.models.dto.PlanetDto;
import com.b2w.starwarsapi.models.form.PlanetForm;
import com.b2w.starwarsapi.repositories.PlanetRepository;
import com.b2w.starwarsapi.services.SwapiService;


/**
 * @author Marcus Santos
 */
@CrossOrigin
@RestController
@RequestMapping("/planets")
public class PlanetsController {
	
	
	@Autowired
	private PlanetRepository planetRepository;
	
	@Autowired
	private SwapiService swapiService;
	
	
	@GetMapping
	public List<PlanetDto> list(@RequestParam(required = false) String name) {
		
		// if 'name' parameter was provided, use find by name:
		if( name != null ) {
			return PlanetDto.convert(planetRepository.findByName(name));
		}
		
		return PlanetDto.convert( planetRepository.findAll() );
	}
	
	
	@PostMapping
	public ResponseEntity<PlanetDto> save(@RequestBody @Valid PlanetForm planetForm, UriComponentsBuilder uriBuilder) {
		
		Planet planetToSave = planetForm.convert();
		
		// Trying to find a planet with the same name in SWAP API, 
		// if find any result, set the collection of films in the object to be saved: 
		swapiService
			.findPlanetByName( planetForm.getName() )
			.ifPresent(searchPlanetResult -> {
				if( searchPlanetResult.hasResults() ) {
					planetToSave.setFilms( searchPlanetResult.getFirstResult().getFilms() );
				}
			});
		
		// Saving the planet, build uri path to the new created planet and send response:
		Planet savedPlanet = planetRepository.save( planetToSave );
		URI uri = uriBuilder.path("/planets/{id}").buildAndExpand( savedPlanet.toObjectIdString() ).toUri();
		
		return ResponseEntity.created(uri).body( PlanetDto.of(savedPlanet) );
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
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remove(@PathVariable String id) {
		
		if ( !ObjectId.isValid(id) ) {
			return ResponseEntity.badRequest().body("The id passed is not a valid ObjectId");
		}
		
		long deleteCount = planetRepository.delete(id);
		
		if(deleteCount == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A Planet with id " + id + " was not found to be deleted");
		}
		
		//return ResponseEntity.noContent().build();
		return ResponseEntity.ok().build();
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
