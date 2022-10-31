package com.digitalhouse.catalogservice.domain.model.DTO;


import java.io.Serializable;

public class ChaptersDTO implements Serializable {

    private String id;
    private String name;
    private Integer number;
    private String urlStream;

    public ChaptersDTO() {
        //No-args constructor
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }

    public String getUrlStream() {
        return urlStream;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setUrlStream(String urlStream) {
        this.urlStream = urlStream;
    }

    @Override
    public String toString() {
        return "ChaptersDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", urlStream='" + urlStream + '\'' +
                '}';
    }
}
