package com.sistema.examenes.entidades;

import java.util.List;

public class HorarioMasivoRequest {

    private List<Long> usuariosIds;
    private Horario horario;
    private boolean dividirPorDias;

    public List<Long> getUsuariosIds() {
        return usuariosIds;
    }

    public void setUsuariosIds(List<Long> usuariosIds) {
        this.usuariosIds = usuariosIds;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public boolean isDividirPorDias() {
        return dividirPorDias;
    }

    public void setDividirPorDias(boolean dividirPorDias) {
        this.dividirPorDias = dividirPorDias;
    }
}

