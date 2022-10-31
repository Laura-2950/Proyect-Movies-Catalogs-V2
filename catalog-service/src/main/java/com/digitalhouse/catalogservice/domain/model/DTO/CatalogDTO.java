package com.digitalhouse.catalogservice.domain.model.DTO;

import java.io.Serializable;
import java.util.List;


public class CatalogDTO implements Serializable {

	private String id;
	private String genre;
	private List<MovieDTO> movies;
	private List<SeriesDTO> series;

	public CatalogDTO() {
		//No-args constructor
	}

	public String getId() {
		return id;
	}

	public String getGenre() {
		return genre;
	}

	public List<MovieDTO> getMovies() {
		return movies;
	}

	public List<SeriesDTO> getSeries() {
		return series;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setMovies(List<MovieDTO> movies) {
		this.movies = movies;
	}

	public void setSeries(List<SeriesDTO> series) {
		this.series = series;
	}

	@Override
	public String toString() {
		return "CatalogDTO{" +
				"id='" + id + '\'' +
				", genre='" + genre + '\'' +
				", movies=" + movies +
				", series=" + series +
				'}';
	}
}
