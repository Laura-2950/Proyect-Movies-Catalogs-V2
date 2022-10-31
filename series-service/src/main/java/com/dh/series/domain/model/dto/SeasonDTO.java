package com.dh.series.domain.model.dto;

import java.util.List;

public class SeasonDTO {
    private String id;
    private Integer seasonNumber;
    private List<ChapterDTO> chapters;

    public SeasonDTO() {
        //No-args constructor
    }

    public String getId() {
        return id;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public List<ChapterDTO> getChapters() {
        return chapters;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public void setChapters(List<ChapterDTO> chapters) {
        this.chapters = chapters;
    }

    @Override
    public String toString() {
        return "SeasonDTO{" +
                "id='" + id + '\'' +
                ", seasonNumber=" + seasonNumber +
                ", chapters=" + chapters +
                '}';
    }
}
