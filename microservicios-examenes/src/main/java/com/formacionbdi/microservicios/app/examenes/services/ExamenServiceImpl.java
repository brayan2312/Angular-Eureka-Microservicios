package com.formacionbdi.microservicios.app.examenes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.microservicios.commons.examenes.models.entity.Asignatura;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import com.formacionbdi.microservicios.app.examenes.models.repository.AsignaturaRepository;
import com.formacionbdi.microservicios.app.examenes.models.repository.ExamenRepository;
import com.formacionbdi.microservicios.commons.services.CommonsServiceImpl;

@Service
public class ExamenServiceImpl extends CommonsServiceImpl<Examen, ExamenRepository> implements ExamenService{

	@Autowired
	private AsignaturaRepository asignaturaRepository;
	@Override
	@Transactional(readOnly = true)
	public List<Examen> findByNombre(String termino) {
		
		return repository.findByNombre(termino);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Asignatura> findAllAsignaturas() {
		return asignaturaRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Long> findExamenesIdConRespuestasByPreguntasIds(Iterable<Long> preguntasIds) {
		return repository.findExamenesIdConRespuestasByPreguntasIds(preguntasIds);
	}

}
