package com.digitalhouse.catalogservice.domain.model.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class MovieHistoryDTO {
    private String id;
    private String genre;
    private List<MovieDTO> movies;
    private LocalDateTime date;

    public MovieHistoryDTO() {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setMovies(List<MovieDTO> movies) {
        this.movies = movies;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MovieHistoryDTO{" +
                "id='" + id + '\'' +
                ", genre='" + genre + '\'' +
                ", movies=" + movies +
                ", date=" + date +
                '}';
    }
}
