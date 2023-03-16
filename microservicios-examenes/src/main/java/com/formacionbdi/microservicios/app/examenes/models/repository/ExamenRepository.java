package com.formacionbdi.microservicios.app.examenes.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;

public interface ExamenRepository extends PagingAndSortingRepository<Examen, Long>{
	
	@Query("SELECT e FROM Examen e WHERE e.nombre LIKE %?1%")
	public List<Examen> findByNombre(String termino);
	
	//4@Query("SELECT e.id FROM Pregunta p join p.examen e WHERE p.id IN ?1 GROUP BY e.id")
	@Query("select e.id from Pregunta p join p.examen e where p.id in ?1 group by e.id")
	public Iterable<Long> findExamenesIdConRespuestasByPreguntasIds(Iterable<Long> preguntasIds);
	
	/*
	 * 
	 * 
	 * 
	@Query("select e from Examen e where e.nombre like %?1%")
	public List<Examen> findByNombre(String term);
	
	@Query("select e.id from Pregunta p join p.examen e where p.id in ?1 group by e.id")
	public Iterable<Long> findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntaIds);
	 */
	
}
