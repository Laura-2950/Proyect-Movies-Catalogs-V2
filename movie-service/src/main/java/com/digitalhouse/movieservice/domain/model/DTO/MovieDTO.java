package com.digitalhouse.movieservice.domain.model.DTO;

import java.io.Serializable;

public class MovieDTO implements Serializable {

    private String id;
    private String name;
    private String genre;
    private String urlStream;

    public MovieDTO() {
        //No-args constructor
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getUrlStream() {
        return urlStream;
    }

    public void setUrlStream(String urlStream) {
        this.urlStream = urlStream;
    }

    @Override
    public String toString() {
        return "{\"Movie\":{"
            + "\"id\":\"" + id + "\""
            + ", \"name\":\"" + name + "\""
            + ", \"genre\":\"" + genre + "\""
            + ", \"urlStream\":\"" + urlStream + "\""
            + "}}";
    }
}
