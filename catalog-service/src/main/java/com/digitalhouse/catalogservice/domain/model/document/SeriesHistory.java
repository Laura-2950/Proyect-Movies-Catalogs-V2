package com.digitalhouse.catalogservice.domain.model.document;

import com.digitalhouse.catalogservice.domain.model.DTO.SeriesDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "Series_History")
public class SeriesHistory {
    @Id
    private String id;
    private String genre;
    private LocalDateTime date;
    private List<SeriesDTO> series;

    public SeriesHistory() {
        //No-args constructor
    }

    public String getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public List<SeriesDTO> getSeries() {
        return series;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setSeries(List<SeriesDTO> series) {
        this.series = series;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SeriesHistory{" +
                "id='" + id + '\'' +
                ", genre='" + genre + '\'' +
                ", series=" + series +
                ", date=" + date +
                '}';
    }
}
