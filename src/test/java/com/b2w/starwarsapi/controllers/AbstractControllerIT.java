package com.b2w.starwarsapi.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.annotation.PostConstruct;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.b2w.starwarsapi.repositories.PlanetRepository;

/**
 * @author Marcus Santos
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AbstractControllerIT {
	
	@LocalServerPort
	protected int port;
	
	@Autowired
	protected TestRestTemplate rest;
	
	@Autowired
	protected PlanetRepository planetRepository;
	
	protected String URL;
	
	
	@PostConstruct
	public void setUp() {
        URL = "http://localhost:" + port;
    }
	
	
	@Before
    public void tearDown() {
        planetRepository.deleteAll();
    }
	
	
	@Test
	public void emptyTestToAvoidErrorInsurefirePlugin() {
		assertTrue(true, "This test must always pass.");
	}
	
	
}
