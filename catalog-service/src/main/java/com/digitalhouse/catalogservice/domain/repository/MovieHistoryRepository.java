package com.digitalhouse.catalogservice.domain.repository;

import com.digitalhouse.catalogservice.domain.model.document.MovieHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieHistoryRepository extends MongoRepository<MovieHistory, String> {
}
