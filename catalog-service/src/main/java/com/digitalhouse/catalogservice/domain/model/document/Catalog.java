package com.digitalhouse.catalogservice.domain.model.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "catalog")
public class Catalog {
	@Id
	private String id;
	private String genre;
	private List<Movie> movies;
	private List<Series> series;

	public Catalog() {
		//No-args constructor
	}

	public Catalog(String genre, List<Movie> movies, List<Series> series) {
		this.genre = genre;
		this.movies = movies;
		this.series = series;
	}

	public String getId() {
		return id;
	}

	public String getGenre() {
		return genre;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public List<Series> getSeries() {
		return series;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	public void setSeries(List<Series> series) {
		this.series = series;
	}

	@Override
	public String toString() {
		return "Catalog{" +
				"id='" + id + '\'' +
				", genre='" + genre + '\'' +
				", movies=" + movies +
				", series=" + series +
				'}';
	}
}
