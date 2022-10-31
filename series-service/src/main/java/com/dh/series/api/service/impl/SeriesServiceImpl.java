package com.dh.series.api.service.impl;


import com.dh.series.api.service.SeriesService;
import com.dh.series.domain.model.document.Series;
import com.dh.series.domain.model.dto.SeriesDTO;
import com.dh.series.domain.repository.SeriesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeriesServiceImpl implements SeriesService {

    @Value("${queue1.series.name}")
    private String seriesQueue;

    private static final Logger LOG = LoggerFactory.getLogger(SeriesServiceImpl.class);

    private final SeriesRepository seriesRepository;
    private final MongoTemplate mongoTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper;


    @Autowired
    public SeriesServiceImpl(SeriesRepository seriesRepository, MongoTemplate mongoTemplate, RabbitTemplate rabbitTemplate, ObjectMapper mapper) {
        this.seriesRepository = seriesRepository;
        this.mongoTemplate=mongoTemplate;
        this.rabbitTemplate=rabbitTemplate;
        this.mapper=mapper;
    }

    @Override
    public SeriesDTO findById(String id) {
        return mapper.convertValue(seriesRepository.findById(id), SeriesDTO.class);
    }

    @Override
    public List<SeriesDTO> findAll() {
        List<SeriesDTO>seriesDTO= new ArrayList<>();
        List<Series> res= seriesRepository.findAll();
        for (Series series: res)
            seriesDTO.add(mapper.convertValue(series, SeriesDTO.class));
        return seriesDTO;
    }

    @Override
    public List<SeriesDTO> findByGenre(String genre) {
        Query query = new Query();
        query.addCriteria(Criteria.where("genre").is(genre));
        List<Series> list = mongoTemplate.find(query, Series.class);
        List<SeriesDTO> seriesDTOS=new ArrayList<>();
        for (Series series: list)
            seriesDTOS.add(mapper.convertValue(series, SeriesDTO.class));
        return seriesDTOS;
    }

    @Override
    public List<SeriesDTO> findByGenre(String genre, Boolean throwError) {
        LOG.info("Se van a buscar las series por género.");
        if (throwError) {
            LOG.error("Hubo un error al buscar las series.");
            throw new RuntimeException();
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("genre").is(genre));
        List<Series> list = mongoTemplate.find(query, Series.class);
        List<SeriesDTO> seriesDTOS=new ArrayList<>();
        for (Series series: list)
            seriesDTOS.add(mapper.convertValue(series, SeriesDTO.class));
        return seriesDTOS;
    }

    @Override
    public SeriesDTO saveSeries(SeriesDTO seriesDTO) throws Exception{
        if (seriesDTO.getName() ==null || seriesDTO.getGenre()==null || seriesDTO.getSeasons()==null) {
            throw new Exception();
        }else{
            Series series= mapper.convertValue(seriesDTO, Series.class);
            SeriesDTO seriesDTO1=mapper.convertValue(seriesRepository.save(series), SeriesDTO.class);
            rabbitTemplate.convertAndSend(seriesQueue, seriesDTO1);
            return seriesDTO1;
        }
    }

}
