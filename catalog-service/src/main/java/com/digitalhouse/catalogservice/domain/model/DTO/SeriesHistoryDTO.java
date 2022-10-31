package com.digitalhouse.catalogservice.domain.model.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class SeriesHistoryDTO {
    private String id;
    private String genre;
    private LocalDateTime date;
    private List<SeriesDTO> series;

    public SeriesHistoryDTO() {
        //No-args constructor
    }

    public String getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<SeriesDTO> getSeries() {
        return series;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setSeries(List<SeriesDTO> series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return "SeriesHistoryDTO{" +
                "id='" + id + '\'' +
                ", genre='" + genre + '\'' +
                ", date=" + date +
                ", series=" + series +
                '}';
    }
}
