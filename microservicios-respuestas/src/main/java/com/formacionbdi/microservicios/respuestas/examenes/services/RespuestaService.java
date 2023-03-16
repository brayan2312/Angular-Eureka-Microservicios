package com.formacionbdi.microservicios.respuestas.examenes.services;

import com.formacionbdi.microservicios.respuestas.examenes.models.entity.Respuesta;

public interface RespuestaService{
	
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);
	
	public Iterable<Respuesta> findRespuestasByAlumnoByExamen(Long alumnoId, Long examenId);
	
	public Iterable<Long> findExamenesIdConRespuestasByAlumno(Long alumnoId);
	
	
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId);
}
