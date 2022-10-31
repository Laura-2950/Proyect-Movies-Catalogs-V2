package com.digitalhouse.movieservice.api.controller;

import java.util.List;

import com.digitalhouse.movieservice.domain.model.DTO.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.digitalhouse.movieservice.api.service.impl.MovieServiceImpl;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Value("${server.port}")
    private String port;

    private final MovieServiceImpl service;

    @Autowired
    public MovieController(MovieServiceImpl movieService) {
        this.service = movieService;
    }

    @GetMapping("/{genre}")
    ResponseEntity<List<MovieDTO>> getMovieByGenre(@PathVariable String genre, HttpServletResponse response)throws Exception{
        response.addHeader("port", port);
        return ResponseEntity.ok().body(service.findByGenre(genre));
    }

    @GetMapping("/withErrors/{genre}")
    ResponseEntity<List<MovieDTO>> getMovieByGenre(@PathVariable String genre, @RequestParam("throwError") boolean throwError) {
        return ResponseEntity.ok().body(service.findByGenre(genre, throwError));
    }

    @PostMapping
    ResponseEntity<MovieDTO> saveMovie(@RequestBody MovieDTO movieDTO, HttpServletResponse response) throws Exception{
        response.addHeader("port", port);
        return ResponseEntity.ok().body(service.saveMovie(movieDTO));
    }
}
