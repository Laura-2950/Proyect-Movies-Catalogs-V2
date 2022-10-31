package com.dh.series;

import com.dh.series.domain.model.document.Chapter;
import com.dh.series.domain.model.document.Season;
import com.dh.series.domain.model.document.Series;
import com.dh.series.domain.repository.SeriesRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@EnableRabbit
@EnableEurekaClient
@SpringBootApplication
public class SeriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeriesApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(SeriesRepository seriesRepository){
		return args ->{
			Chapter chapter1=new Chapter("001","Piloto", 1, "www.netflix.com/Toy_Boy/1/Piloto");
			Chapter chapter2=new Chapter("002","De entre los muertos", 2, "www.netflix.com/Toy_Boy/1/De_entre_los_muertos");
			Chapter chapter3=new Chapter("003","El juicio final", 3, "www.netflix.com/Toy_Boy/1/El_juicio_final");
			List<Chapter>chapters= new ArrayList<>();
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
			Series series1= new Series("Toy Boy", "Drama",seasons );
			seriesRepository.insert(series1);

			//---------------------------

			Chapter chapter7=new Chapter("001","Piloto", 1, "www.netflix.com/Outer_Banks/1/Piloto");
			Chapter chapter8=new Chapter("002","La brujula de la suerte", 2, "www.netflix.com/Outer_Banks/1/La_brujula_de_la_suerte");
			Chapter chapter9=new Chapter("003","La zona prohibida", 3, "www.netflix.com/Outer_Banks/1/La_zona_prohibida");
			List<Chapter>chapters3= new ArrayList<>();
			chapters3.add(chapter7);
			chapters3.add(chapter8);
			chapters3.add(chapter9);
			Season season3=new Season("001", 1, chapters3);

			Chapter chapter10=new Chapter("001","El oro", 1, "www.netflix.com/Outer_Banks/2/El_oro");
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
			Series series2= new Series("Outer Banks", "Acción",seasons1 );
			seriesRepository.insert(series2);

		};

	}
}
