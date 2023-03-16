package com.formacionbdi.microservicios.app.usuarios.services;


import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.services.CommonsService;

public interface AlumnoService extends CommonsService<Alumno>{

	public List<Alumno> findByNombreOrApellido(String term);
	
	public Iterable<Alumno> findAllById(Iterable<Long> ids);
	
	public void eliminarCursoAlumnoPorId(@PathVariable Long id);
}
