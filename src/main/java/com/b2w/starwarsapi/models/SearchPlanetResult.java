package com.b2w.starwarsapi.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object received from SWAP API about searches
 * 
 * @author Marcus Santos
 *
 */
public class SearchPlanetResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Properties:
	private long count;
	private String next;
	private String previous;
	private List<Planet> results = new ArrayList<>();
	
	
	// Constructors:
	public SearchPlanetResult() {
		
	}
	
	
	// Getters and Setters
	
	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public List<Planet> getResults() {
		return results;
	}

	public void setResults(List<Planet> results) {
		this.results = results;
	}
	
	public boolean hasResults() {
		return (this.results.size() > 0);
	}
	
	// End Getters and Setters
	
	
	public Planet getFirstResult() {
		return this.results.stream().findFirst().get();
	}
	
	
	@Override
	public String toString() {
		return "SearchPlanetResultDto [count=" + count + ", next=" + next + ", previous=" + previous + ", results="
				+ results + "]";
	}
	
}
