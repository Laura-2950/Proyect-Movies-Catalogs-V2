package com.dh.series.api.service;

import com.dh.series.domain.model.dto.SeriesDTO;

import java.util.List;

public interface SeriesService {
    public SeriesDTO findById(String id);
    public List<SeriesDTO> findAll();
    public List<SeriesDTO> findByGenre(String genre);
    public List<SeriesDTO> findByGenre(String genre, Boolean throwError);
    public SeriesDTO saveSeries(SeriesDTO seriesDTO) throws Exception;
}
