package com.digitalhouse.catalogservice.domain.model.DTO;

import java.io.Serializable;
import java.util.List;

public class SeasonDTO implements Serializable {

    private String  id;
    private Integer seasonNumber;
    private List<ChaptersDTO> chapters;


    public SeasonDTO() {
        //No-args constructor
    }

    public String getId() {
        return id;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public List<ChaptersDTO> getChapters() {
        return chapters;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public void setChapters(List<ChaptersDTO> chapters) {
        this.chapters = chapters;
    }

    @Override
    public String toString() {
        return "SeasonDTO{" +
                "id=" + id +
                ", seasonNumber=" + seasonNumber +
                ", chapters=" + chapters +
                '}';
    }
}
