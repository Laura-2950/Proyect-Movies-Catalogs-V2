package com.digitalhouse.catalogservice.api.service;

import com.digitalhouse.catalogservice.domain.model.DTO.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CatalogService {
    public CatalogDTO getCatalogByGenre(String genre) throws Exception;
    public ResponseEntity<List<MovieDTO>> findMovieByGenre(String genre, Boolean throwError);
    public ResponseEntity<List<SeriesDTO>> findSeriesByGenre(String genre, Boolean throwError);
    public List<MovieHistoryDTO> getMovieHistory();
    public List<SeriesHistoryDTO> getSeriesHistory();

}
