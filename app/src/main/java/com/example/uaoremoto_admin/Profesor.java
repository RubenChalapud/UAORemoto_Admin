package com.example.uaoremoto_admin;

public class Profesor {
    private String idprofesor;
    private String nombreprofesor;
    private String apellidoprofesor;
    private String correoprofesor;
    private String contraseñaprofesor;
    private String sintomasprofesor;

    public Profesor(String idprofesor, String nombreprofesor, String apellidoprofesor, String correoprofesor, String contraseñaprofesor, String sintomasprofesor){
        this.idprofesor=idprofesor;
        this.nombreprofesor=nombreprofesor;
        this.apellidoprofesor=apellidoprofesor;
        this.correoprofesor=correoprofesor;
        this.contraseñaprofesor=contraseñaprofesor;
        this.sintomasprofesor=sintomasprofesor;
    }

    public Profesor(){

    }

    public String getIdprofesor() {
        return idprofesor;
    }

    public void setIdprofesor(String idprofesor) {
        this.idprofesor = idprofesor;
    }

    public String getNombreprofesor() {
        return nombreprofesor;
    }

    public void setNombreprofesor(String nombreprofesor) {
        this.nombreprofesor = nombreprofesor;
    }

    public String getApellidoprofesor() {
        return apellidoprofesor;
    }

    public void setApellidoprofesor(String apellidoprofesor) {
        this.apellidoprofesor = apellidoprofesor;
    }

    public String getCorreoprofesor() {
        return correoprofesor;
    }

    public void setCorreoprofesor(String correoprofesor) {
        this.correoprofesor = correoprofesor;
    }

    public String getContraseñaprofesor() {
        return contraseñaprofesor;
    }

    public void setContraseñaprofesor(String contraseñaprofesor) {
        this.contraseñaprofesor = contraseñaprofesor;
    }

    public String getSintomasprofesor() {
        return sintomasprofesor;
    }

    public void setSintomasprofesor(String sintomasprofesor) {
        this.sintomasprofesor = sintomasprofesor;
    }
}

