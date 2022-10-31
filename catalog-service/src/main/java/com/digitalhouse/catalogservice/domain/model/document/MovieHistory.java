package com.digitalhouse.catalogservice.domain.model.document;

import com.digitalhouse.catalogservice.domain.model.DTO.MovieDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "Movie_History")
public class MovieHistory {
    @Id
    private String id;
    private String genre;
    private LocalDateTime date;
    private List<MovieDTO> movies;

    public MovieHistory() {
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
        return "MovieHistory{" +
                "id='" + id + '\'' +
                ", genre='" + genre + '\'' +
                ", date=" + date +
                ", movies=" + movies +
                '}';
    }
}
