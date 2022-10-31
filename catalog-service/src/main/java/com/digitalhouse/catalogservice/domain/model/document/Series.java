package com.digitalhouse.catalogservice.domain.model.document;

import java.util.List;

public class Series {

    private String id;
    private String name;
    private String genre;
    private List<Season> seasons;

    public Series() {
        //No-args constructor
    }

    public Series(String id, String name, String genre, List<Season> seasons) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.seasons = seasons;
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

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setSeasons(List<Season> seasons) {
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
