package com.example.lab6_20175947_20180252;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    String  correo, contraseña;
    List<ActividadItem> actividades;

    public Usuario(){

    }

    public Usuario(String correo, String contraseña) {
        this.correo = correo;
        this.contraseña = contraseña;
        this.actividades = new ArrayList<>();
    }

    public void agregarActividad(ActividadItem actividad) {
        actividades.add(actividad);
    }

    public void eliminarActividad(ActividadItem actividad) {
        actividades.remove(actividad);
    }



    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
