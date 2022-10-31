package com.digitalhouse.catalogservice.domain.model.DTO;

import java.io.Serializable;
import java.util.List;

public class SeriesDTO implements Serializable {

    private String id;
    private String name;
    private String genre;
    private List<SeasonDTO> seasons;

    public SeriesDTO() {
        //No-args constructor
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public List<SeasonDTO> getSeasons() {
        return seasons;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setSeasons(List<SeasonDTO> seasons) {
        this.seasons = seasons;
    }

    @Override
    public String toString() {
        return "SerieDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", seasons=" + seasons +
                '}';
    }
}
