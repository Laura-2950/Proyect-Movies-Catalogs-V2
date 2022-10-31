package com.dh.series.api.controller;

import java.util.List;
import java.util.Objects;

import com.dh.series.domain.model.dto.SeriesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dh.series.api.service.impl.SeriesServiceImpl;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/series")
public class SeriesController {

    @Value("${server.port}")
    private String port;

    private final SeriesServiceImpl service;

    @Autowired
    public SeriesController(SeriesServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<SeriesDTO> seriesList = service.findAll();
        return seriesList.isEmpty()
            ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
            : ResponseEntity.ok(seriesList);
    }

    @GetMapping("/{genre}")
    ResponseEntity<List<SeriesDTO>> getSeriesByGenre(@PathVariable String genre, HttpServletResponse response){
        response.addHeader("port", port);
        return ResponseEntity.ok().body(service.findByGenre(genre));
    }

    @GetMapping("/withErrors/{genre}")
    ResponseEntity<List<SeriesDTO>> getSeriesByGenre(@PathVariable String genre, @RequestParam("throwError") boolean throwError) {
        return ResponseEntity.ok().body(service.findByGenre(genre, throwError));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SeriesDTO> findById(@PathVariable String id) {
        SeriesDTO seriesDTO = service.findById(id);
        return Objects.isNull(seriesDTO)
            ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
            : ResponseEntity.ok(seriesDTO);
    }

    @PostMapping
    public ResponseEntity<SeriesDTO> saveSeries(@RequestBody SeriesDTO seriesDTO) throws Exception {
        return ResponseEntity.ok(service.saveSeries(seriesDTO));
    }
}
