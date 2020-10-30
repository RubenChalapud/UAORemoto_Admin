package com.example.uaoremoto_admin;

public class Curso {
    private String idcurso;
    private String nombrecurso;
    private String numestudiantes;
    private String horariocurso;
    private String idprofesor;
    private String idaula;

    public Curso(String idcurso, String nombrecurso, String numestudiantes, String horariocurso, String idprofesor, String idaula){
        this.idcurso = idcurso;
        this.nombrecurso = nombrecurso;
        this.numestudiantes = numestudiantes;
        this.horariocurso = horariocurso;
        this.idprofesor = idprofesor;
        this.idaula = idaula;
    }

    public Curso(){

    }

    public String getIdcurso(){
        return idcurso;
    }

    public void setIdcurso(String idcurso){
        this.idcurso = idcurso;
    }

    public String getNombrecurso(){
        return nombrecurso;
    }

    public void setNombrecurso(String nombrecurso){
        this.nombrecurso = nombrecurso;
    }

    public String getNumestudiantes(){
        return numestudiantes;
    }

    public void setNumestudiantes(String numestudiantes){
        this.numestudiantes = numestudiantes;
    }

    public String getHorariocurso() {
        return horariocurso;
    }

    public void setHorariocurso(String horariocurso) {
        this.horariocurso = horariocurso;
    }

    public String getIdaula() {
        return idaula;
    }

    public void setIdaula(String idaula) {
        this.idaula = idaula;
    }

    public String getIdprofesor() {
        return idprofesor;
    }

    public void setIdprofesor(String idprofesor) {
        this.idprofesor = idprofesor;
    }
}
