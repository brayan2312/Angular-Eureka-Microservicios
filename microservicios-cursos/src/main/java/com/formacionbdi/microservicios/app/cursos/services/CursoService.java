package com.formacionbdi.microservicios.app.cursos.services;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.formacionbdi.microservicios.app.cursos.models.entity.Curso;
import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.services.CommonsService;

public interface CursoService extends CommonsService<Curso>{

	public Curso findCursoByAlumnoId(Long id);
	
	public Iterable<Long> obtenerExamenesIdsConRespuestaAlumno(Long alumnoId);
	
	public Iterable<Alumno> obtenerAlumnosPorCurso(List<Long> ids);
	
	public void eliminarCursoAlumnoPorId(Long id);
	
}
