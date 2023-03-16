package com.formacionbdi.microservicios.app.examenes.services;

import java.util.List;

import com.formacionbdi.microservicios.commons.examenes.models.entity.Asignatura;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import com.formacionbdi.microservicios.commons.services.CommonsService;

public interface ExamenService extends CommonsService<Examen>{

	public List<Examen> findByNombre(String termino);
	
	public Iterable<Asignatura> findAllAsignaturas();
	
	public Iterable<Long> findExamenesIdConRespuestasByPreguntasIds(Iterable<Long> preguntasIds);
}
