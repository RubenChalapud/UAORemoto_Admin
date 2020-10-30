package com.example.uaoremoto_admin;

public class Aula {
    private String idaula;
    private String nombreaula;
    private String latitud;
    private String longitud;

    public Aula(String idaula, String nombreaula, String latitud, String longitud){
        this.idaula = idaula;
        this.nombreaula = nombreaula;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Aula(){

    }

    public String getIdaula() {
        return idaula;
    }

    public void setIdaula(String idaula) {
        this.idaula = idaula;
    }

    public String getNombreaula() {
        return nombreaula;
    }

    public void setNombreaula(String nombreaula) {
        this.nombreaula = nombreaula;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
