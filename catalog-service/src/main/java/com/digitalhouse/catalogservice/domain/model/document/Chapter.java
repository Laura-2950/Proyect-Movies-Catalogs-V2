package com.digitalhouse.catalogservice.domain.model.document;


public class Chapter {
    private String id;
    private String name;
    private Integer number;
    private String urlStream;

    public Chapter() {
        //No-args constructor
    }

    public Chapter(String id, String name, Integer number, String urlStream) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.urlStream = urlStream;
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
