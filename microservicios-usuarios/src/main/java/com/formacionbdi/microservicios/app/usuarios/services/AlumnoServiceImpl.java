package com.formacionbdi.microservicios.app.usuarios.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.app.usuarios.client.CursoFeignClient;
import com.formacionbdi.microservicios.app.usuarios.models.repository.AlumnoRepository;
import com.formacionbdi.microservicios.commons.services.CommonsServiceImpl;

@Service
public class AlumnoServiceImpl extends CommonsServiceImpl<Alumno,AlumnoRepository> implements AlumnoService {

	
	@Autowired
	private CursoFeignClient client;
	
	@Override
	@Transactional( readOnly = true)
	public List<Alumno> findByNombreOrApellido(String term) {
		
		return repository.findByNombreOrApellido(term);
	}

	@Override
	@Transactional( readOnly = true)
	public Iterable<Alumno> findAllById(Iterable<Long> ids) {
		return repository.findAllById(ids);
	}

	@Override
	public void eliminarCursoAlumnoPorId(Long id) {
		
		client.eliminarCursoAlumnoPorId(id);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		super.deleteById(id);
		this.eliminarCursoAlumnoPorId(id);
	}

	@Override
	public Iterable<Alumno> findAll() {
		return repository.findAllByOrderByIdAsc();
	}

	@Override
	public Page<Alumno> findAll(Pageable pageable) {
		return repository.findAllByOrderByIdAsc(pageable);
	}

	
	

	
}
