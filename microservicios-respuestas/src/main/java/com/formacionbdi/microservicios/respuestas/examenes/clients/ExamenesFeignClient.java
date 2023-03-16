package com.formacionbdi.microservicios.respuestas.examenes.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;

@FeignClient("microservicio-examenes")
public interface ExamenesFeignClient {
	
	@GetMapping("/{id}")
	public Examen obtenerExamenPorId(@PathVariable Long id);
	
	@GetMapping("/respondidos-por-preguntas")
	public List<Long> obtenerExamenesIdsPorPreguntasIdsRespondidas(@RequestParam List<Long> preguntaIds);
}
 