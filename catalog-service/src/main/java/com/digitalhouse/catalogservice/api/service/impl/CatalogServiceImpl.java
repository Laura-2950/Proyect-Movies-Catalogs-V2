package com.digitalhouse.catalogservice.api.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.digitalhouse.catalogservice.api.client.SeriesClient;
import com.digitalhouse.catalogservice.api.service.CatalogService;
import com.digitalhouse.catalogservice.domain.model.DTO.*;
import com.digitalhouse.catalogservice.domain.model.document.Catalog;
import com.digitalhouse.catalogservice.domain.model.document.MovieHistory;
import com.digitalhouse.catalogservice.domain.model.document.SeriesHistory;
import com.digitalhouse.catalogservice.domain.repository.CatalogRepository;
import com.digitalhouse.catalogservice.domain.repository.MovieHistoryRepository;
import com.digitalhouse.catalogservice.domain.repository.SeriesHistoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.digitalhouse.catalogservice.api.client.MovieClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;

/**
 Aplicamos el patrón Circuit Breaker para las request que realiza catálogo a los microservicios series-service y movie-service, microservicios cooperativos de catalog-service, y en caso de falla, establecemos
 un flujo alternativo mediante los respectivos fallback. Optamos por aplicarlo ahí por que tanto movie-service como series-service también tienen sus respectivos endpoints para recibir request por lo que puede
 generar una sobrecarga de los microservicios y para hacer más tolerante a fallas nuestra aplicación aplicamos el patrón para que si ocurre un error, éste pueda recuperarse y el sistema siga funcionando y no opere fallas en cascada.
 Los métodos que tienen Circuit Breaker reciben un parámetro booleano, que de ser true lanzan una RuntimeException en los microservicios cooperativos, pudiéndose observar la gestión de eventos del patrón en cuestión.
 Configuraciones (https://github.com/Laura-2950/spring-cloud-config/blob/main/movie-catalog-II/catalog-service-dev.yml ):
 slidingWindowType: Se establece una configuración basada en eventos.
 slidingWindowSize: Se define en 5 la cantidad de llamadas para que Circuit Breaker pase a estado open(cuando el servicio destino falla).
 failureRateThreshold: Se configura un porcentaje del 50 % de llamadas fallidas que hacen que Circuit Breaker pase a estado open.
 automaticTransitionFromOpenToHalfOpenEnabled: Se configura automático el pasaje al estado half-open una vez que el tiempo de espera se cumple.
 waitDurationInOpenState: Se define en 15 segundos el tiempo en el que debe pasar del estado open, al de half-open.
 permittedNumberOfCallsInHalfOpenState: El patrón espera 3 llamadas para determinar a qué estado pasará. Como seteamos el failureRateThreshold en 50%, si cada 2 de 3 llamadas fallan, volvemos al estado open, sino pasa a closed.
 También se configuran colas de mensajería asincrónica con RabbintMQ para los servicios no tan críticos como la actualización de los catálogos cuando se agrega una película o una serie.
 Para la comunicación entre los microservicios cooperativos se usa spring Cloud Open Feing en conjunto con Spring Cloud LoadBalancer que, en caso de tener múltiples instancias de un servicio lo cual lo hace aun más tolerante a fallas,
 determina a qué instancia envía cada solicitud balancenado la carga.
 Por último, dado que los servicios detrás de api-gateway podrían comportarse mal y afectar a nuestros clientes, envolvemos las rutas que creamos en interruptores automáticos.
 Esto se implementa a través de un filtro simple que puede agregar a sus solicitudes.
 Cuando ocurre un problema en la ruta envuelta del disyuntor, llama /fallbacka la aplicación Gateway y ejecuta los métodos definidos para cada microservicio.
 La elección de catalog-service y api-gateway para la aplicación de esquemas de resilencia es basicamente por que son los dos servicios que se comunican con otros microservicios para cumplir sus funciones y por eso tienen mayor carga
 como lo podemos ver en las imágenes del monitoreo con zipkin en archivo Readme.
 */

@Service
public class CatalogServiceImpl implements CatalogService {

    @Value("${queue.movie.name}")
    private String movieQueue;

    @Value("${queue1.series.name}")
    private String seriesQueue;

    private final Logger LOG = LoggerFactory.getLogger(CatalogServiceImpl.class);

    private final MovieClient movieClient;
    private final SeriesClient seriesClient;
    private final CatalogRepository catalogRepository;
    private final SeriesHistoryRepository seriesHistoryRepository;
    private final MovieHistoryRepository movieHistoryRepository;
    private final ObjectMapper mapper;

    @Autowired
    public CatalogServiceImpl(MovieClient movieClient, SeriesClient seriesClient, CatalogRepository catalogRepository, SeriesHistoryRepository seriesHistoryRepository, MovieHistoryRepository movieHistoryRepository, ObjectMapper mapper) {
        this.movieClient = movieClient;
        this.seriesClient=seriesClient;
        this.catalogRepository=catalogRepository;
        this.seriesHistoryRepository=seriesHistoryRepository;
        this.movieHistoryRepository=movieHistoryRepository;
        this.mapper=mapper;
    }

    @Override
    public CatalogDTO getCatalogByGenre(String genre) throws Exception{
        Optional<Catalog> catalog=catalogRepository.findByGenre(genre);
        CatalogDTO catalogDTO = null;
        if (catalog.isPresent()){
            catalogDTO= mapper.convertValue(catalog,CatalogDTO.class);
        }
        return catalogDTO;
    }

    @CircuitBreaker(name = "movies", fallbackMethod = "moviesFallbackMethod")
    @Override
    public ResponseEntity<List<MovieDTO>> findMovieByGenre(String genre, Boolean throwError) {
        LOG.info("Se va a incluir el llamado al movie-service...");
        ResponseEntity<List<MovieDTO>> listMovies=movieClient.getMovieByGenreWithThrowError(genre, throwError);
        MovieHistory movieHistory=new MovieHistory();
        movieHistory.setMovies(listMovies.getBody());
        movieHistory.setGenre(genre);
        movieHistory.setDate(LocalDateTime.now());
        movieHistoryRepository.save(movieHistory);
        return listMovies;
    }

    private ResponseEntity<List<MovieDTO>> moviesFallbackMethod(CallNotPermittedException exception) {
        LOG.info("se activó el circuitbreaker");
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }


    @CircuitBreaker(name = "series", fallbackMethod = "seriesFallbackMethod")
    @Override
    public ResponseEntity<List<SeriesDTO>> findSeriesByGenre(String genre, Boolean throwError) {
        LOG.info("Se va a incluir el llamado al series-service...");
        ResponseEntity<List<SeriesDTO>> listSeries= seriesClient.getSeriesByGenreWithThrowError(genre, throwError);
        SeriesHistory seriesHistory= new SeriesHistory();
        seriesHistory.setSeries(listSeries.getBody());
        seriesHistory.setGenre(genre);
        seriesHistory.setDate(LocalDateTime.now());
        seriesHistoryRepository.save(seriesHistory);
        return listSeries;
    }

    private ResponseEntity<List<SeriesDTO>> seriesFallbackMethod(CallNotPermittedException exception) {
        LOG.info("se activó el circuitbreaker");
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @RabbitListener(queues = "${queue.movie.name}")
    public void saveMovie(MovieDTO movieDTO) throws Exception {
        LOG.info("Se recibio una movie a través de rabbit " + movieDTO.toString());
        String genre= movieDTO.getGenre();
        CatalogDTO catalogDTO= getCatalogByGenre(genre);
        if (Objects.nonNull(catalogDTO)){
            List<MovieDTO>movies= catalogDTO.getMovies();
            movies.add(movieDTO);
            catalogDTO.setMovies(movies);
            updateCatalog(catalogDTO);
        }else {
            CatalogDTO catalogDTO1= new CatalogDTO();
            catalogDTO1.setGenre(movieDTO.getGenre());
            List<MovieDTO>movies= new ArrayList<>();
            movies.add(movieDTO);
            catalogDTO1.setMovies(movies);
            saveNewCatalog(catalogDTO1);
        }

    }

    @RabbitListener(queues = "${queue1.series.name}")
    public void saveSeries(SeriesDTO seriesDTO) throws Exception {

        LOG.info("Se recibio una serie a través de rabbit " + seriesDTO.toString());
        String genre= seriesDTO.getGenre();
        CatalogDTO catalogDTO= getCatalogByGenre(genre);
        if (Objects.nonNull(catalogDTO)){
            List<SeriesDTO>series= catalogDTO.getSeries();
            series.add(seriesDTO);
            catalogDTO.setSeries(series);
            updateCatalog(catalogDTO);
        }else {
            CatalogDTO catalogDTO1= new CatalogDTO();
            catalogDTO1.setGenre(seriesDTO.getGenre());
            List<SeriesDTO>series= new ArrayList<>();
            series.add(seriesDTO);
            catalogDTO1.setSeries(series);
            saveNewCatalog(catalogDTO1);
        }

    }


    public void saveNewCatalog(CatalogDTO catalogDTO){
        Catalog catalog=mapper.convertValue(catalogDTO, Catalog.class);
        catalogRepository.insert(catalog);
    }

    public void updateCatalog(CatalogDTO catalogDTO){
        Catalog catalog=mapper.convertValue(catalogDTO, Catalog.class);
        catalogRepository.save(catalog);
    }

    @Override
    public List<MovieHistoryDTO> getMovieHistory(){
        List<MovieHistory>movieHistories=movieHistoryRepository.findAll();
        List<MovieHistoryDTO>movieHistoryDTOS=new ArrayList<>();
        for (MovieHistory movieHistory:movieHistories) {
            movieHistoryDTOS.add(mapper.convertValue(movieHistory, MovieHistoryDTO.class));
        }
        return movieHistoryDTOS;
    }

    @Override
    public List<SeriesHistoryDTO> getSeriesHistory(){
        List<SeriesHistory>seriesHistories=seriesHistoryRepository.findAll();
        List<SeriesHistoryDTO>seriesHistoryDTOS=new ArrayList<>();
        for (SeriesHistory seriesHistory:seriesHistories) {
            seriesHistoryDTOS.add(mapper.convertValue(seriesHistory, SeriesHistoryDTO.class));
        }
        return seriesHistoryDTOS;
    }


}
