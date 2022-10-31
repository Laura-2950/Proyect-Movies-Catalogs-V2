package com.digitalhouse.movieservice.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.digitalhouse.movieservice.api.service.MovieService;
import com.digitalhouse.movieservice.domain.model.DTO.MovieDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.digitalhouse.movieservice.domain.model.entity.Movie;
import com.digitalhouse.movieservice.domain.model.MovieInfo;
import com.digitalhouse.movieservice.domain.repositories.MovieRepository;
import com.digitalhouse.movieservice.util.RedisUtils;

@Service
public class MovieServiceImpl implements MovieService {

    @Value("${queue.movie.name}")
    private String movieQueue;

    private static final Logger LOG = LoggerFactory.getLogger(MovieServiceImpl.class);

    private final MovieRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final RedisUtils redisUtils;
    private final ObjectMapper mapper;


    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, RabbitTemplate rabbitTemplate, RedisUtils redisUtils, ObjectMapper mapper) {
        this.repository = movieRepository;
        this.rabbitTemplate=rabbitTemplate;
        this.redisUtils = redisUtils;
        this.mapper=mapper;
    }

    @Override
    public List<MovieDTO> findByGenre(String genre) throws Exception{
        MovieInfo movieInfo = redisUtils.getMovieInfo(genre);
        List <MovieDTO> moviesDTO=new ArrayList<>();
        if (Objects.nonNull(movieInfo)) {
            for (Movie movie : movieInfo.getMovies())
                moviesDTO.add(mapper.convertValue(movie, MovieDTO.class));
            return moviesDTO;
        }
        List<Movie> movies = repository.findByGenre(genre);
        if (movies.isEmpty()){
            throw new Exception();
        }
        redisUtils.createMovieInfo(genre, movies);
        for (Movie movie: movies)
            moviesDTO.add(mapper.convertValue(movie, MovieDTO.class));
        return moviesDTO;
    }

    @Override
    public List<MovieDTO> findByGenre(String genre, Boolean throwError) {
        LOG.info("se van a buscar las peliculas por género");
        if (throwError) {
            LOG.error("Hubo un error al buscar las películas");
            throw new RuntimeException();
        }
        List<Movie>movies=repository.findByGenre(genre);
        List<MovieDTO> moviesDTO=new ArrayList<>();
        for (Movie movie: movies)
            moviesDTO.add(mapper.convertValue(movie, MovieDTO.class));
        return moviesDTO;
    }

    @Override
    public MovieDTO saveMovie(MovieDTO movieDTO) throws Exception{
        if (movieDTO.getName() ==null || movieDTO.getGenre()==null || movieDTO.getUrlStream()==null) {
            throw new Exception();
        }else{
            Movie movie= mapper.convertValue(movieDTO, Movie.class);
            MovieDTO movieDTO1=mapper.convertValue(repository.save(movie), MovieDTO.class);
            rabbitTemplate.convertAndSend(movieQueue, movieDTO1);
            return movieDTO1;
        }
    }
}
