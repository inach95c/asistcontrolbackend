package com.sistema.examenes.dto;

import com.sistema.examenes.entidades.Horario;
import lombok.Data;

@Data
public class HorarioDTO {
 //   private Horario.DiaSemana diaSemana;
    private String horaEntrada; // Formato "HH:mm"
    private String horaSalida;
    private Long usuarioId;
}
