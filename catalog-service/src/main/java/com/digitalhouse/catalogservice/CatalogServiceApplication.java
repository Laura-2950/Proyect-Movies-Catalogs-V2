package com.digitalhouse.catalogservice;

import com.digitalhouse.catalogservice.domain.model.document.*;
import com.digitalhouse.catalogservice.domain.repository.CatalogRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@EnableEurekaClient
@EnableFeignClients
@EnableRabbit
@SpringBootApplication
public class CatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(CatalogRepository catalogRepository){
        return args ->{
            Chapter chapter1=new Chapter("001","Piloto", 1, "www.netflix.com/Toy_Boy/1/Piloto");
            Chapter chapter2=new Chapter("002","De entre los muertos", 2, "www.netflix.com/Toy_Boy/1/De_entre_los_muertos");
            Chapter chapter3=new Chapter("003","El juicio final", 3, "www.netflix.com/Toy_Boy/1/El_juicio_final");
            List<Chapter> chapters= new ArrayList<>();
            chapters.add(chapter1);
            chapters.add(chapter2);
            chapters.add(chapter3);
            Season season1=new Season("001", 1, chapters);

            Chapter chapter4=new Chapter("001","La persona a la que más quieres", 1, "www.netflix.com/Toy_Boy/2/La-persona_a_la_que_mas_quieres");
            Chapter chapter5=new Chapter("002","Que es el sexo para ti", 2, "www.netflix.com/Toy_Boy/2/que_es_el_sexo_para_ti");
            Chapter chapter6=new Chapter("003","El precio de los dioses", 3, "www.netflix.com/Toy_Boy/2/El_precio_de_los_dioses");
            List<Chapter>chapters2= new ArrayList<>();
            chapters2.add(chapter4);
            chapters2.add(chapter5);
            chapters2.add(chapter6);
            Season season2=new Season("002", 2, chapters2);

            List<Season>seasons=new ArrayList<>();
            seasons.add(season1);
            seasons.add(season2);
            Series series1= new Series("001","Toy Boy", "Drama",seasons );


            //---------------------------

            Chapter chapter7=new Chapter("001","Piloto", 1, "www.netflix.com/Outer_Banks/1/Piloto");
            Chapter chapter8=new Chapter("002","La brujula de la suerte", 2, "www.netflix.com/Outer_Banks/1/La_brujula_de_la_suerte");
            Chapter chapter9=new Chapter("003","La zona prohibida", 3, "www.netflix.com/Outer_Banks/1/La_zona_prohibida");
            List<Chapter>chapters3= new ArrayList<>();
            chapters3.add(chapter7);
            chapters3.add(chapter8);
            chapters3.add(chapter9);
            Season season3=new Season("001", 1, chapters3);

            Chapter chapter10=new Chapter("001","El oro", 1, "www.netflix.com/Outer_Banks/2/E_oro");
            Chapter chapter11=new Chapter("002","El robo", 2, "www.netflix.com/Outer_Banks/2/El_robo");
            Chapter chapter12=new Chapter("003","Plegarias", 3, "www.netflix.com/Outer_Banks/2/Plegarias");
            List<Chapter>chapters4= new ArrayList<>();
            chapters4.add(chapter10);
            chapters4.add(chapter11);
            chapters4.add(chapter12);
            Season season4=new Season("002", 2, chapters4);

            List<Season>seasons1=new ArrayList<>();
            seasons1.add(season3);
            seasons1.add(season4);
            Series series2= new Series("002","Outer Banks", "Acción",seasons1 );

            //-------------------

            Movie movie1=new Movie("001","Lou","Drama","www.netflix.com/Lou");
            Movie movie2=new Movie("002","Inseparables","Drama","www.netflix.com/Inseparables");
            Movie movie4=new Movie("003","Beckett","Acción","www.netflix.com/Beckett");
            Movie movie5=new Movie("004","El hombre gris","Acción","www.netflix.com/El_hombre_gris");
            //-----------------

            List<Series>series=new ArrayList<>();
            series.add(series1);
            List<Movie>movies=new ArrayList<>();
            movies.add(movie1);
            movies.add(movie2);
            Catalog catalog1= new Catalog("Drama", movies, series);
            catalogRepository.insert(catalog1);

            List<Series>series3=new ArrayList<>();
            series3.add(series2);
            List<Movie>movies3=new ArrayList<>();
            movies3.add(movie4);
            movies3.add(movie5);
            Catalog catalog2= new Catalog("Acción", movies3, series3);
            catalogRepository.insert(catalog2);

        };

    }
}
