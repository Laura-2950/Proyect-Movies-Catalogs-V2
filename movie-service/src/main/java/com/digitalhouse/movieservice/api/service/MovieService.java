package com.digitalhouse.movieservice.api.service;

import com.digitalhouse.movieservice.domain.model.DTO.MovieDTO;

import java.util.List;

public interface MovieService {
    public List<MovieDTO> findByGenre(String genre) throws Exception;
    public List<MovieDTO> findByGenre(String genre, Boolean throwError);
    public MovieDTO saveMovie(MovieDTO movieDTO) throws Exception;
}
