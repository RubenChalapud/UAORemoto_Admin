package com.example.uaoremoto_admin;

public class Estudiante {
    private String idestudiante;
    private String nombreestudiante;
    private String apellidoestudiante;
    private String correoestudiante;
    private String contraseñaestudiante;
    private String sintomasestudiante;

    public Estudiante(String idestudiante, String nombreestudiante, String apellidoestudiante, String correoestudianteestudiante, String contraseñaestudiante, String sintomasestudiante){
        this.idestudiante = idestudiante;
        this.nombreestudiante = nombreestudiante;
        this.apellidoestudiante = apellidoestudiante;
        this.correoestudiante = correoestudianteestudiante;
        this.contraseñaestudiante = contraseñaestudiante;
        this.sintomasestudiante = sintomasestudiante;
    }

    public Estudiante(){

    }

    public String getIdestudiante() {
        return idestudiante;
    }

    public void setIdestudiante(String idestudiante) {
        this.idestudiante = idestudiante;
    }

    public String getNombreestudiante() {
        return nombreestudiante;
    }

    public void setNombreestudiante(String nombreestudiante) {
        this.nombreestudiante = nombreestudiante;
    }

    public String getApellidoestudiante() {
        return apellidoestudiante;
    }

    public void setApellidoestudiante(String apellidoestudiante) {
        this.apellidoestudiante = apellidoestudiante;
    }

    public String getCorreoestudiante() {
        return correoestudiante;
    }

    public void setCorreoestudiante(String correoestudiante) {
        this.correoestudiante = correoestudiante;
    }

    public String getContraseñaestudiante() {
        return contraseñaestudiante;
    }

    public void setContraseñaestudiante(String contraseñaestudiante) {
        this.contraseñaestudiante = contraseñaestudiante;
    }

    public String getSintomasestudiante() {
        return sintomasestudiante;
    }

    public void setSintomasestudiante(String sintomasestudiante) {
        this.sintomasestudiante = sintomasestudiante;
    }
}
