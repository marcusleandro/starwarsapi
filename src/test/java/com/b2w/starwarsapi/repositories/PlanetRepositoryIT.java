package com.b2w.starwarsapi.repositories;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.b2w.starwarsapi.models.Planet;

/**
 * @author Marcus Santos
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PlanetRepositoryIT {
	
	
	@Autowired
	private PlanetRepository planetRepository;
	
	@Before
    public void tearDown() {
        planetRepository.deleteAll();
    }
	
	
	@Test
	public void shouldReturnCreatedPlanet() {
		Planet planet = createPlanet("Planet Blue", "Temperate", "Blue Planet");
		assertNotNull("Planet shouldn't be null", planet);
		assertThat("Planet id shouldn't be null", planet.getId(), notNullValue());
		assertThat(planet.getName(), equalTo("Planet Blue"));
		assertThat(planet.getClimate(), equalTo("Temperate"));
		assertThat(planet.getTerrain(), equalTo("Blue Planet"));
	}
	
	
	@Test
	public void shouldReturnNullForNotExistingPlanet() {
		Optional<Planet> optionalPlanet = planetRepository.findOne( new ObjectId().toString() );
		assertFalse(optionalPlanet.isPresent());
	}
	
	
	@Test
	public void shoudReturnFoundPlanet() {
		Planet planet = createPlanet("Planet to be found", "Temperate", "Montains");
		
		Optional<Planet> optionalFoundPlanet = planetRepository.findOne(planet.getId().toString());
		assertTrue(optionalFoundPlanet.isPresent());
		
		Planet foundPlanet = optionalFoundPlanet.get();
		
		assertThat(foundPlanet.getName(), equalTo("Planet to be found"));
		assertThat(foundPlanet.getClimate(), equalTo("Temperate"));
		assertThat(foundPlanet.getTerrain(), equalTo("Montains"));
	}
	
	
	@Test
	public void shouldRemovePlanet() {
		Planet planet = createPlanet("Planet to be removed", "frozen", "tundra, ice caves, mountain ranges");
		String id = planet.getId().toString();
		long count = planetRepository.delete(id);
		assertThat(count, equalTo(1l));
		Optional<Planet> optional = planetRepository.findOne( new ObjectId().toString() );
		assertFalse(optional.isPresent());
	}
	
	
	@Test
	public void shouldUpdateChangeAndPersistData() {
		Planet planet = createPlanet("Planet X", "frozen", "grassy hills, swamps, forests, mountains");
		planet.setName("Planet XYZ");
		planet.setClimate("Temperate");
		Planet updatedPlanet = planetRepository.update(planet);
		assertThat(updatedPlanet.getName(), equalTo("Planet XYZ"));
		assertThat(updatedPlanet.getClimate(), equalTo("Temperate"));
	}
	
	
	@Test
	public void shouldfindByName() {
		createPlanet("Planet XXX", "frozen", "grassy hills, swamps, forests, mountains");
		createPlanet("Planet XXX", "temperate", "forests, mountains");
		List<Planet> findByName = planetRepository.findByName("Planet XXX");
		assertThat(findByName.size(), equalTo(2));
	}
	
	
	@Test
	public void shouldfindAll() {
		Planet planet1 = createPlanet("Planet XXX", "frozen", "grassy hills, swamps, forests, mountains");
		Planet planet2 = createPlanet("Planet XYZ", "temperate", "mountains");
		Planet planet3 = createPlanet("Planet XVW", "temperate hot", "forests");
		
		
		List<Planet> planets = planetRepository.findAll();
		
		assertThat(planets.size(), equalTo(3));
		assertThat(planets, hasItems(planet1, planet2, planet3));
	}
	
	
	@Test
	public void shouldCount() {
		createPlanet("Planet XXX", "frozen", "grassy hills, swamps, forests, mountains");
		createPlanet("Planet XYZ", "temperate", "mountains");
		createPlanet("Planet XVW", "temperate hot", "forests");
		long count = planetRepository.count();
		assertThat(count, equalTo(3l));
	}
	
	
	@Test
	public void shouldDeleteAll() {
		createPlanet("Planet XXX", "frozen", "grassy hills, swamps, forests, mountains");
		createPlanet("Planet XYZ", "temperate", "mountains");
		long count = planetRepository.deleteAll();
		assertThat(count, equalTo(2l));
		
		List<Planet> foundPlanets = planetRepository.findAll();
		assertThat(foundPlanets.size(), equalTo(0));
	}
	
	
	
	private Planet createPlanet(String name, String climate, String terrain) {
		Planet planet = new Planet(name, climate, terrain);
		return planetRepository.save(planet);
	}
	
	
}
