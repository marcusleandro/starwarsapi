package com.b2w.starwarsapi.repositories;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.ReturnDocument.AFTER;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.b2w.starwarsapi.models.Planet;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;

/**
 * @author Marcus Santos
 * 
 */
@Repository
public class PlanetRepositoryImpl implements PlanetRepository {
	
	private static final String collectionName = "planets";
	
	@Value("${spring.data.mongodb.database}")
    private String databaseName;
	
	@Autowired
    private MongoClient client;
	
	private MongoCollection<Planet> planetCollection;
	
	
	@PostConstruct
	void init() {
		planetCollection = client.getDatabase(databaseName).getCollection(collectionName, Planet.class);
	}
	
	
	@Override
	public Planet save(Planet planet) {
		planet.setId(new ObjectId());
		planetCollection.insertOne(planet);
		return planet;
	}

	
	@Override
	public List<Planet> findAll() {
		return planetCollection.find().into(new ArrayList<>());
	}

	
	@Override
	public List<Planet> findByName(String name) {
		return planetCollection.find(Filters.eq("name", name), Planet.class).into(new ArrayList<>());
	}

	
	@Override
	public Optional<Planet> findOne(String id) {
		Planet planet = planetCollection.find(eq("_id", new ObjectId(id))).first();
		return Optional.ofNullable(planet);
	}

	
	@Override
	public long count() {
		return planetCollection.countDocuments();
	}

	
	@Override
	public long delete(String id) {
		return planetCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
	}
	

	@Override
	public long deleteAll() {
		return planetCollection.deleteMany(new BsonDocument()).getDeletedCount();
	}
	
	
	@Override
	public Planet update(Planet planet) {
		FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return planetCollection.findOneAndReplace(eq("_id", planet.getId()), planet, options);
	}

}
