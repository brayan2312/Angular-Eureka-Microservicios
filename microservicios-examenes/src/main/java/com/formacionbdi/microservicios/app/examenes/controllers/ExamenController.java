package com.formacionbdi.microservicios.app.examenes.controllers;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Pregunta;
import com.formacionbdi.microservicios.app.examenes.services.ExamenService;
import com.formacionbdi.microservicios.commons.controllers.CommonsController;

@RestController
public class ExamenController extends CommonsController<Examen, ExamenService> {
	
	@GetMapping("/respondidos-por-preguntas")
	public ResponseEntity<?> obtenerExamenesIdsPorPreguntasIdsRespondidas(@RequestParam List<Long> preguntaIds){			
		return ResponseEntity.ok().body(service.findExamenesIdConRespuestasByPreguntasIds(preguntaIds));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Validated @RequestBody Examen examen, @PathVariable Long id, BindingResult result) {

		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Examen> o = service.findById(id);

		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Examen dbExamen = o.get();
		dbExamen.setNombre(examen.getNombre());
		/*
		List<Pregunta> eliminadas = new ArrayList<>();
		dbExamen.getPreguntas().forEach(pdb -> {
			if (!examen.getPreguntas().contains(pdb)) {
				eliminadas.add(pdb);
			}
		});
		
		eliminadas.forEach( p -> {
			dbExamen.removePregunta(p);
		});
		*/
		
		List<Pregunta> eliminadas= dbExamen.getPreguntas()
		.stream()
		.filter(pdb -> !examen.getPreguntas().contains(pdb))
		.collect(Collectors.toList());
		
		eliminadas.forEach(dbExamen::removePregunta);
		
		dbExamen.setPreguntas(examen.getPreguntas());
		dbExamen.setAsignaturaHija(examen.getAsignaturaHija());
		dbExamen.setAsignaturaPadre(examen.getAsignaturaPadre());

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dbExamen));
	}
	
	@GetMapping("/filtrar/{termino}")
	public ResponseEntity<?> filtrar(@PathVariable String termino){
		return ResponseEntity.ok(this.service.findByNombre(termino));
	}

	@GetMapping("/asignaturas")
	public ResponseEntity<?> filtrarAsignaturas(){
		return ResponseEntity.ok(this.service.findAllAsignaturas());
	}
	
	
	
}
