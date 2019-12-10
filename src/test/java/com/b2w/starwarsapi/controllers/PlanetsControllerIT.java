package com.b2w.starwarsapi.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.b2w.starwarsapi.models.Planet;
import com.b2w.starwarsapi.models.dto.PlanetDto;
import com.b2w.starwarsapi.models.form.PlanetForm;


/**
 * @author Marcus Santos
 */
public class PlanetsControllerIT extends AbstractControllerIT {
	
	
	@DisplayName("GET /planets with 2 planets")
	@Test
	public void shouldGetPlanets() {
		// GIVEN
		Planet planet1 = createPlanet("Planet 1", "Tropical", "Rocks");
		Planet planet2 = createPlanet("Planet 2", "Temperate", "Rain Forest");
		List<PlanetDto> planetsDto = Arrays.asList(new PlanetDto(planet1), new PlanetDto(planet2));
		// WHEN
		ResponseEntity<List<PlanetDto>> result = rest.exchange(URL + "/planets", HttpMethod.GET, null, new ParameterizedTypeReference<List<PlanetDto>>() {});
		// THEN
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody()).containsExactlyInAnyOrderElementsOf(planetsDto);
	}
	
	
	@DisplayName("GET /planets?name=Planet+1 by Name with 2 planets")
	@Test
	public void shouldGetPlanetsByName() {
		// GIVEN
		Planet planet1 = createPlanet("Planet XYZ", "Tropical", "Rocks");
		Planet planet2 = createPlanet("Planet XYZ", "Temperate", "Rain Forest");
		List<PlanetDto> planetsDto = Arrays.asList(new PlanetDto(planet1), new PlanetDto(planet2));
		// WHEN
		ResponseEntity<List<PlanetDto>> result = rest.exchange(URL + "/planets?name=Planet XYZ", HttpMethod.GET, null, new ParameterizedTypeReference<List<PlanetDto>>() {});
		// THEN
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody()).containsExactlyInAnyOrderElementsOf(planetsDto);
	}
	
	
	@DisplayName("GET /person/{id}")
	@Test
	public void shouldGetPlanetById() {
		// GIVEN
		Planet planetInserted = createPlanet("Planet X", "Temperate", "Rain Forest");
		ObjectId idInserted = planetInserted.getId();
		// WHEN
		ResponseEntity<PlanetDto> result = rest.getForEntity(URL + "/planets/" + idInserted, PlanetDto.class);
		// THEN
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(new PlanetDto(planetInserted));
	}
	
	
	@DisplayName("POST /planets 1 planet")
	@Test
	public void postPlanet() {
		// WHEN
		ResponseEntity<PlanetDto> result = rest.postForEntity(URL + "/planets", new PlanetForm("Planet Blue", "Temperate", "Forest"), PlanetDto.class);
		// THEN
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		PlanetDto planetDto = result.getBody();
		assertNotNull(planetDto.getId());
		assertThat(planetDto.getName()).isEqualTo("Planet Blue");
		assertThat(planetDto.getClimate()).isEqualTo("Temperate");
		assertThat(planetDto.getTerrain()).isEqualTo("Forest");
	}
	
	
	@DisplayName("POST /planets 1 invalid planet")
	@Test
	public void postInvalidPlanet() {
		// WHEN
		System.out.println("rest" + rest);
		ResponseEntity<?> result = rest.postForEntity(URL + "/planets", new PlanetForm(null, "Temperate", "Forest"), Object.class);
		// THEN
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		//Object body = result.getBody();
	}
	
	
	@DisplayName("DELETE /planets/{id}")
    @Test
    public void deletePersonById() {
		// GIVEN
		Planet planet = createPlanet("Planet to be deleted", "Tropical", "Rocks");
		ObjectId idInserted = planet.getId();
		// WHEN
		ResponseEntity<Long> result = rest.exchange(URL + "/planets/" + idInserted.toString(), HttpMethod.DELETE, null, new ParameterizedTypeReference<Long>() {});
		// THEN
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(planetRepository.count()).isEqualTo(0L);
	}
	
	
	@DisplayName("GET /planets/count")
    @Test
    public void shouldGetPlanetsCount() {
		// GIVEN
		createPlanet("Planet 1", "Tropical", "Rocks");
		createPlanet("Planet 2", "Temperate", "Rain Forest");
		createPlanet("Planet 3", "Desert", "Desert and Sand");
		// WHEN
		ResponseEntity<Long> result = rest.getForEntity(URL + "/planets/count", Long.class);
		// THEN
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(3L);
	}
	
	
	
	private Planet createPlanet(String name, String climate, String terrain) {
		Planet planet = new Planet(name, climate, terrain);
		return planetRepository.save(planet);
	}
}
