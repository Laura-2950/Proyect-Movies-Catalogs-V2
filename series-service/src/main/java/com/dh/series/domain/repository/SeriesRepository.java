package com.dh.series.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.dh.series.domain.model.document.Series;

@Repository
public interface SeriesRepository extends MongoRepository<Series, String>{
}
