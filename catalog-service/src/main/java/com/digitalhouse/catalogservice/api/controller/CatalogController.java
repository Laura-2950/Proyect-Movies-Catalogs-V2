package com.digitalhouse.catalogservice.api.controller;

import java.util.List;
import java.util.Objects;

import com.digitalhouse.catalogservice.domain.model.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.digitalhouse.catalogservice.api.service.impl.CatalogServiceImpl;

@RestController
@RequestMapping("/catalogs")
public class CatalogController {

    private final CatalogServiceImpl catalogServiceImpl;

    @Autowired
    public CatalogController(CatalogServiceImpl catalogServiceImpl) {
        this.catalogServiceImpl = catalogServiceImpl;
    }

    @GetMapping("/{genre}")
    public ResponseEntity<CatalogDTO>getCatalogByGenre(@PathVariable String genre) throws Exception {
        CatalogDTO catalogDTO= catalogServiceImpl.getCatalogByGenre(genre);
        return !Objects.nonNull(catalogDTO)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(catalogDTO, HttpStatus.OK);
    }

    @GetMapping("/withErrors/movies/{genre}")
    public ResponseEntity<List<MovieDTO>> getMovieByGenre(@PathVariable String genre, @RequestParam("throwError") Boolean throwError) {
        return catalogServiceImpl.findMovieByGenre(genre, throwError);
    }

    @GetMapping("/withErrors/series/{genre}")
    public ResponseEntity<List<SeriesDTO>> getSeriesByGenre(@PathVariable String genre, @RequestParam("throwError") Boolean throwError) {
        return catalogServiceImpl.findSeriesByGenre(genre, throwError);
    }

    @GetMapping("/movieHistory")
    public ResponseEntity<List<MovieHistoryDTO>> getMovieHistory() {
        return new ResponseEntity<>(catalogServiceImpl.getMovieHistory(), HttpStatus.OK);
    }

    @GetMapping("/seriesHistory")
    public ResponseEntity<List<SeriesHistoryDTO>> getSeriesHistory() {
        return new ResponseEntity<>(catalogServiceImpl.getSeriesHistory(), HttpStatus.OK);
    }

}
