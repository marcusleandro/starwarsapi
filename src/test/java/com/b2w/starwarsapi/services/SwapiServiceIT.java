package com.b2w.starwarsapi.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.b2w.starwarsapi.models.Planet;
import com.b2w.starwarsapi.models.SearchPlanetResult;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SwapiServiceIT {
	
	@Autowired
	private SwapiService swapiService;
	
	@Test
	public void searchForValidPlanetName() {
		
		String validName = "Coruscant";
		Optional<SearchPlanetResult> optional = swapiService.findPlanetByName(validName);
		
		assertTrue(optional.isPresent());
		
		SearchPlanetResult searchResult = optional.get();
		System.out.println(searchResult);
		
		assertTrue(searchResult.hasResults());
		
		Planet planet = searchResult.getFirstResult();
		System.out.println(planet);
		
		assertThat(planet.getName(), equalTo(validName));
		
	}
	
	
	@Test
	public void searchForInvalidPlanetName() {
		
		String invalidName = "Trapd56sdvsdkvd";
		Optional<SearchPlanetResult> optional = swapiService.findPlanetByName(invalidName);
		
		assertTrue( optional.isPresent() );
		
		SearchPlanetResult searchResult = optional.get();
		System.out.println(searchResult);
		
		assertFalse(searchResult.hasResults());
	}

}
