package com.formacionbdi.microservicios.app.cursos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.microservicios.app.cursos.models.entity.Curso;
import com.formacionbdi.microservicios.app.cursos.models.entity.CursoAlumno;
import com.formacionbdi.microservicios.app.cursos.services.CursoService;
import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.controllers.CommonsController;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;

@RestController
public class CursoController extends CommonsController<Curso, CursoService> {

	@Value("$ {config.balanceador.test}")
	private String balanceadorTest;

	Logger logger = LoggerFactory.getLogger(CommonsController.class);

	@DeleteMapping("/eliminar-alumno/{id}")
	public ResponseEntity<?> eliminarCursoAlumnoPorId(@PathVariable Long id) {
		service.eliminarCursoAlumnoPorId(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	@GetMapping
	public ResponseEntity<?> listar() {
		List<Curso> cursos = ((List<Curso>) service.findAll()).stream().map(c -> {
			c.getCursoAlumnos().forEach(ca -> {
				Alumno alumno = new Alumno();
				alumno.setId(ca.getAlumnoId());
				c.addAlumnos(alumno);
			});
			return c;
		}).collect(Collectors.toList());

		return ResponseEntity.ok().body(cursos);
	}

	@GetMapping("/pagina")
	@Override
	public ResponseEntity<?> listar(Pageable pageable) {
		Page<Curso> cursos = service.findAll(pageable).map(curso -> {
			curso.getCursoAlumnos().forEach(ca -> {
				Alumno alumno = new Alumno();
				alumno.setId(ca.getAlumnoId());
				curso.addAlumnos(alumno);
			});
			return curso;
		});
		return ResponseEntity.ok().body(cursos);
	}

	@GetMapping("/{id}")
	@Override
	public ResponseEntity<?> verDetalle(@PathVariable Long id) {
		Optional<Curso> o = service.findById(id);

		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Curso curso = o.get();

		if (curso.getCursoAlumnos().isEmpty() == false) {
			List<Long> ids = curso.getCursoAlumnos().stream().map(ca -> ca.getAlumnoId()).collect(Collectors.toList());

			List<Alumno> alumnos = (List<Alumno>) service.obtenerAlumnosPorCurso(ids);
			curso.setAlumnos(alumnos);
		}
		return ResponseEntity.ok(curso);

	}

	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("balanceador", balanceadorTest);
		response.put("cursos", service.findAll());
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id) {
		Optional<Curso> o = this.service.findById(id);

		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Curso dbCurso = o.get();

		dbCurso.setNombre(curso.getNombre());

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}

	@PutMapping("/{id}/asignar-alumnos")
	public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {
		Optional<Curso> o = this.service.findById(id);

		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Curso dbCurso = o.get();

		alumnos.forEach(a -> {
			CursoAlumno cursoAlumno = new CursoAlumno();
			cursoAlumno.setAlumnoId(a.getId());
			cursoAlumno.setCurso(dbCurso);
			dbCurso.addCursoAlumno(cursoAlumno);
		});

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}

	@PutMapping("/{id}/eliminar-alumno")
	public ResponseEntity<?> eliminarAlumnos(@RequestBody Alumno alumno, @PathVariable Long id) {
		Optional<Curso> o = this.service.findById(id);

		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Curso dbCurso = o.get();
		CursoAlumno cursoAlumno = new CursoAlumno();
		cursoAlumno.setAlumnoId(alumno.getId());
		dbCurso.removeCursoAlumno(cursoAlumno);

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}

	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> buscarPorAlumnosId(@PathVariable Long id) {
		Curso curso = service.findCursoByAlumnoId(id);
		// return ResponseEntity.ok(curso);

		if (curso != null) {
			List<Long> examenesIds = (List<Long>) service.obtenerExamenesIdsConRespuestaAlumno(id);
			
			if (examenesIds != null && examenesIds.size() > 0) {

				List<Examen> examenes = curso.getExamenes().stream().map(examen -> {

					if (examenesIds.contains(examen.getId())) {
						examen.setRespondido(true);

					}
					return examen;
				}).collect(Collectors.toList());

				curso.setExamenes(examenes);
			}
		}
		return ResponseEntity.ok(curso);
	}

	@PutMapping("/{id}/asignar-examenes")
	public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id) {
		Optional<Curso> o = this.service.findById(id);

		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Curso dbCurso = o.get();

		examenes.forEach(e -> {
			dbCurso.addExamen(e);
		});

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}

	@PutMapping("/{id}/eliminar-examen")
	public ResponseEntity<?> eliminarAlumnos(@RequestBody Examen examen, @PathVariable Long id) {
		Optional<Curso> o = this.service.findById(id);

		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Curso dbCurso = o.get();

		dbCurso.removeExamen(examen);

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}

}
