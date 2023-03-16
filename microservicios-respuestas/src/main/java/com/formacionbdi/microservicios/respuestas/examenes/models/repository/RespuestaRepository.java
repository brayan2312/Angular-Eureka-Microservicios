package com.formacionbdi.microservicios.respuestas.examenes.models.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.formacionbdi.microservicios.respuestas.examenes.models.entity.Respuesta;

public interface RespuestaRepository extends MongoRepository<Respuesta, String>{
	
	@Query("{'alumnoId': ?0, preguntaId: { $in: ?1} }")
	public Iterable<Respuesta> findRespuestasByAlumnoByPreguntaIds(Long alumnoId, Iterable<Long> preguntaIds);
	
	@Query("{'alumnoId': ?0 }")
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId);
	
	
		
	/*
	@Query("SELECT r FROM Respuesta r  JOIN fetch r.pregunta p JOIN fetch p.examen e WHERE r.alumnoId = ?1 AND e.id = ?2")
	public Iterable<Respuesta> findRespuestasByAlumnoByExamen(Long alumnoId, Long examenId);
	
	@Query("SELECT e.id FROM Respuesta r join r.pregunta p join p.examen e WHERE r.alumnoId=?1 GROUP BY e.id")
	public Iterable<Long> findExamenesIdConRespuestasByAlumno(Long alumnoId);
	 */
	@Query("{'alumnoId': ?0, 'pregunta.examen.id': ?1 }")
	public Iterable<Respuesta> findRespuestasByAlumnoByExamen(Long alumnoId, Long examenId);
	
	@Query(value = "{'alumnoId': ?0 }", fields = "{ 'pregunta.examen.id': 1 }")
	public Iterable<Respuesta> findExamenesIdConRespuestasByAlumno(Long alumnoId);
}
