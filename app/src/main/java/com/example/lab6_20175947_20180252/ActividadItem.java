package com.example.lab6_20175947_20180252;

import java.time.LocalDate;
import java.time.LocalTime;

public class ActividadItem {
    String actividad_ID;
    String titulo;
    LocalDate fecha;
    LocalTime hora_inicio, hora_fin;

    public ActividadItem(){

    }


    public ActividadItem(String actividad_ID, String titulo, LocalDate fecha, LocalTime hora_inicio, LocalTime hora_fin) {
        this.actividad_ID = actividad_ID;
        this.titulo = titulo;
        this.fecha = fecha;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
    }

    public String getActividad_ID() {
        return actividad_ID;
    }

    public void setActividad_ID(String actividad_ID) {
        this.actividad_ID = actividad_ID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(LocalTime hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public LocalTime getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(LocalTime hora_fin) {
        this.hora_fin = hora_fin;
    }
}
