package com.sistema.examenes.controladores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.examenes.dto.PreguntaDTO;
import com.sistema.examenes.entidades.Examen;
import com.sistema.examenes.entidades.Pregunta;
import com.sistema.examenes.servicios.ExamenService;
import com.sistema.examenes.servicios.PreguntaService;

@RestController
@RequestMapping("/pregunta")
@CrossOrigin("*")    
public class PreguntaController {
	
	@Autowired
	private PreguntaService preguntaService;
	 
	
	@Autowired
	private ExamenService examenService;
	
	@PostMapping("/")
	public ResponseEntity<Pregunta> guardarPregunta(@RequestBody Pregunta pregunta){
		return ResponseEntity.ok(preguntaService.agregarPregunta(pregunta));
		
	}
	
	@PutMapping("/")
	public ResponseEntity<Pregunta> actualizarPregunta(@RequestBody Pregunta pregunta){
		return ResponseEntity.ok(preguntaService.actualizarPregunta(pregunta));
		
	}
	
	@GetMapping("/examen/{examenId}")
	public ResponseEntity<?> ListarPreguntasDelExamen(@PathVariable("examenId") Long examenId ){
		Examen examen = examenService.obtenerExamen(examenId); 
		Set<Pregunta> preguntas = examen.getPreguntas();
		
		
		List examenes = new ArrayList(preguntas);
		if(examenes.size() > Integer.parseInt(examen.getNumeroDePreguntas())) {
		examenes = examenes.subList(0,Integer.parseInt(examen.getNumeroDePreguntas() + 1));	
		}
		
		Collections.shuffle(examenes);
        return ResponseEntity.ok(examenes);
		
	}	
	
	
	@GetMapping("/{preguntaId}")
    public Pregunta listarPreguntaPorId(@PathVariable("preguntaId") Long preguntaId){
        return preguntaService.obtenerPregunta(preguntaId);
    }  
	
	@DeleteMapping("/{preguntaId}")
    public void eliminarPregunta(@PathVariable("preguntaId") Long preguntaId){
        preguntaService.eliminarPregunta(preguntaId);
    }
	
	 @GetMapping("/examen/todos/{examenId}")
	    public ResponseEntity<?> listarPreguntaDelExamenComoAdministrador(@PathVariable("examenId") Long examenId){
	        Examen examen = new Examen();
	        examen.setExamenId(examenId);
	        Set<Pregunta> preguntas = preguntaService.obtenerPreguntasDelExamen(examen);
	        return ResponseEntity.ok(preguntas);
	    }
	 
	 // todo esto es del videa apartir de minuto 8:58:51 esto es para start.html
	 
	 @PostMapping("/evaluar-examen")
	    public ResponseEntity<?> evaluarExamen(@RequestBody List<Pregunta> preguntas){
	        double puntosMaximos = 0;
	        Integer respuestasCorrectas = 0;
	        Integer intentos = 0;
      //     String estadoDeCalificacionDeLosCentros1 = null;   //txd
	        

	        for(Pregunta p : preguntas){
	            Pregunta pregunta = this.preguntaService.listarPregunta(p.getPreguntaId());
	           // if(pregunta.getRespuesta().equals(p.getRespuestaDada())){
	      //          pregunta.getRespuesta();          // txd
	                respuestasCorrectas ++;
	                double puntos = Double.parseDouble(preguntas.get(0).getExamen().getPuntosMaximos())/preguntas.size();
	                puntosMaximos += puntos;
	                //mio txd
	 //            estadoDeCalificacionDeLosCentros = pregunta.getEstadoDeCalificacionDeLosCentros();   //txd
	           //    this.preguntaService.actualizarPregunta(pregunta);    //mio txd
	                
	       //     }// if
	            if(p.getRespuestaDada() != null){
	                intentos ++;
	            }
	        }  // for

	        Map<String,Object> respuestas = new HashMap<>();
	     //   Map<String,Object> estadoDeCalificacionDeLosCentros = new HashMap<>();      // txd

	        respuestas.put("puntosMaximos",puntosMaximos);                     //esto es lo que sale en imprimir
	        respuestas.put("respuestasCorrectas",respuestasCorrectas);         // con esto puedo jugar para mostrar en pantalla
	        respuestas.put("intentos",intentos);
	     //   respuestas.put("estadoDeCalificacionDeLosCentros1",estadoDeCalificacionDeLosCentros1);   // txd  
	        return ResponseEntity.ok(respuestas);
	     
	    }
	 
/*	 @PostMapping("/evaluar-examen")
	    public ResponseEntity<?> evaluarExamen(@RequestBody Pregunta pregunta){
		 return ResponseEntity.ok(preguntaService.actualizarPregunta(pregunta)); 
	 }		 
*/	 
	 
	 ////////////////////////////////////////////// txd ///////////////////////////////////
	 
	 @PutMapping("/pregunta/actualizar-estado")
	    public ResponseEntity<?> actualizarEstado(@RequestBody PreguntaDTO datos) {
	        try {
	            preguntaService.actualizarEstado(datos.getId(), datos.getEstadoDeCalificacionDeLosCentros());
	            return ResponseEntity.ok("Estado actualizado correctamente");
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body("Error al actualizar el estado: " + e.getMessage());
	        }
	    }

	  ////////////////////////////////////////////// txd ///////////////////////////////////
	 
}//fin
