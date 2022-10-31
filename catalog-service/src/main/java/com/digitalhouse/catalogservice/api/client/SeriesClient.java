package com.digitalhouse.catalogservice.api.client;

import com.digitalhouse.catalogservice.api.config.LoadBalancerConfig;
import com.digitalhouse.catalogservice.domain.model.DTO.SeriesDTO;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "series-service")
@LoadBalancerClient(name = "series-service", configuration = LoadBalancerConfig.class)
public interface SeriesClient {
    @GetMapping("/series/{genre}")
    ResponseEntity<List<SeriesDTO>> getSeriesByGenre(@PathVariable(value = "genre") String genre);

    @GetMapping("/series/withErrors/{genre}")
    ResponseEntity<List<SeriesDTO>> getSeriesByGenreWithThrowError(@PathVariable(value = "genre") String genre,
                                                                 @RequestParam("throwError") boolean throwError);
}
