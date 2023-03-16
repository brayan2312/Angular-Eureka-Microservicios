package com.formacionbdi.microservicios.respuestas.examenes.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
//import com.formacionbdi.microservicios.commons.examenes.models.entity.Pregunta;
//import com.formacionbdi.microservicios.respuestas.examenes.clients.ExamenesFeignClient;
import com.formacionbdi.microservicios.respuestas.examenes.models.entity.Respuesta;
import com.formacionbdi.microservicios.respuestas.examenes.models.repository.RespuestaRepository;

@Service
public class RespuestaServiceImpl implements RespuestaService{
	Logger logger = LoggerFactory.getLogger(RespuestaServiceImpl.class);
	
	//@Autowired
	//private ExamenesFeignClient examenClient;
	
	@Autowired
	private RespuestaRepository repository;
	
	@Override
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		return repository.saveAll(respuestas);
	}
	
	@Override
	public Iterable<Respuesta> findRespuestasByAlumnoByExamen(Long alumnoId, Long examenId) {
		/*Examen examen = examenClient.obtenerExamenPorId(examenId);
		List<Pregunta> preguntas = examen.getPreguntas();
		List<Long> preguntaIds = preguntas.stream().map(p -> p.getId()).collect(Collectors.toList());
		List<Respuesta> respuestas = (List<Respuesta>) repository.findRespuestasByAlumnoByPreguntaIds(alumnoId, preguntaIds);
		respuestas = respuestas.stream().map(r -> {
			preguntas.forEach(p -> {
				if(p.getId() == r.getPreguntaId()) {
					r.setPregunta(p);
				}
			});
			return r;
		}).collect(Collectors.toList());*/
		List<Respuesta> respuestas = (List<Respuesta>) repository.findRespuestasByAlumnoByExamen(alumnoId, examenId);
		return respuestas;
		
		
		//return repository.findRespuestasByAlumnoByExamen(alumnoId, examenId);
	}

	@Override
	public Iterable<Long> findExamenesIdConRespuestasByAlumno(Long alumnoId) {
		/*
		List<Respuesta> respuestasAlumno = (List<Respuesta>) repository.findByAlumnoId(alumnoId);
		List<Long> examenIds = Collections.emptyList();
		
		if(respuestasAlumno.size() > 0) {
		  List<Long> preguntaIds = respuestasAlumno.stream().map(r -> r.getPreguntaId()).collect(Collectors.toList());		 
		  examenIds = examenClient.obtenerExamenesIdsPorPreguntasIdsRespondidas(preguntaIds); // AQUI
		}
		
		*/
		List<Respuesta> respuestasAlumno = (List<Respuesta>) repository.findExamenesIdConRespuestasByAlumno(alumnoId);
		List<Long> examenIds = respuestasAlumno.stream().map(r -> r.getPregunta().getExamen().getId())
				.distinct()
				.collect(Collectors.toList());
		return examenIds;
	}

	@Override
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {

		return repository.findByAlumnoId(alumnoId);
	}

}
