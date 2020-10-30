package com.example.uaoremoto_admin;


public class Clase {
    private String idclase;
    private String modoclase;
    private String asistenciaclase;
    private String fechaclase;
    private String idcurso;
    private String idestudiante;

    public Clase(String idclase, String modoclase, String asistenciaclase, String fechaclase, String idcurso, String idestudiante){
        this.idclase = idclase;
        this.modoclase = modoclase;
        this.asistenciaclase = asistenciaclase;
        this.fechaclase = fechaclase;
        this.idcurso = idcurso;
        this.idestudiante = idestudiante;
    }

    public Clase(){

    }

    public String getIdclase() {
        return idclase;
    }

    public void setIdclase(String idclase) {
        this.idclase = idclase;
    }

    public String getModoclase() {
        return modoclase;
    }

    public void setModoclase(String modoclase) {
        this.modoclase = modoclase;
    }

    public String getAsistenciaclase() {
        return asistenciaclase;
    }

    public void setAsistenciaclase(String asistenciaclase) {
        this.asistenciaclase = asistenciaclase;
    }

    public String getFechaclase() {
        return fechaclase;
    }

    public void setFechaclase(String fechaclase) {
        this.fechaclase = fechaclase;
    }

    public String getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(String idcurso) {
        this.idcurso = idcurso;
    }

    public String getIdestudiante() {
        return idestudiante;
    }

    public void setIdestudiante(String idestudiante) {
        this.idestudiante = idestudiante;
    }

}

