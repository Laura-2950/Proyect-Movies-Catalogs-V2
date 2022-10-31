package com.dh.series.domain.model.dto;

import java.util.List;

public class SeriesDTO {
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
        return "SeriesDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", seasons=" + seasons +
                '}';
    }
}
