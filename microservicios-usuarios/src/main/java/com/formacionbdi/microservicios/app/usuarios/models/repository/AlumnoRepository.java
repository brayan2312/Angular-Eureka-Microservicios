package com.formacionbdi.microservicios.app.usuarios.models.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;

public interface AlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {

	@Query("SELECT a FROM Alumno a WHERE upper(a.nombre) LIKE upper(concat('%', ?1, '%')) OR upper(a.apellido) LIKE upper(concat('%', ?1, '%'))")
	public List<Alumno> findByNombreOrApellido(String term);
	
	public Iterable<Alumno> findAllByOrderByIdAsc();
	
	public Page<Alumno> findAllByOrderByIdAsc(Pageable pageable);
}
