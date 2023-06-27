package com.example.lab6_20175947_20180252;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ActividadItem {
    String actividad_ID;
    String titulo;
    String fecha;
    String hora_inicio, hora_fin;

    public ActividadItem(){

    }


    public ActividadItem(String actividad_ID, String titulo, String fecha, String hora_inicio, String hora_fin) {
        this.actividad_ID = actividad_ID;
        this.titulo = titulo;
        this.fecha = fecha;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
    }

    private String fechaToString(LocalDate fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return fecha.format(formatter);
    }

    private LocalDate stringToLocalDate(String fechaString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        return LocalDate.parse(fechaString, formatter);
    }

    private Long horaToLong(LocalTime hora) {
        return hora.toNanoOfDay() / 1000000L;
    }

    private LocalTime longToHora(Long horaLong) {
        return LocalTime.ofNanoOfDay(horaLong * 1000000L);
    }

    /////


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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }
}
