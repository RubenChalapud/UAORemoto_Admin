package com.example.uaoremoto_admin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EstudianteList extends ArrayAdapter<Estudiante> {
    private Activity context;

    //Lista de profesores
    List<Estudiante> Estudiantes;

    public EstudianteList(Activity context, List<Estudiante> Estudiantes){
        super(context, R.layout.layout_estudiante_list, Estudiantes);
        this.context = context;
        this.Estudiantes = Estudiantes;
    }

    // clase adpatadora para desplegar el listado de elementos almacenados en Firebase
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_estudiante_list, null, true);
        //initialize String idprofesor, String nombreprofesor, String apellidoprofesor, String correoprofesor, String contraseñaprofesor, String sintomasprofesor
        TextView textViewIdEstudiante = (TextView) listViewItem.findViewById(R.id.textViewIdEstudiante);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewNombreEstudiante);
        TextView textviewApellido = (TextView) listViewItem.findViewById(R.id.textviewApellidoEstudiante);
        TextView textviewCorreo = (TextView) listViewItem.findViewById(R.id.textviewCorreoEstudiante);
        TextView textviewContraseña = (TextView) listViewItem.findViewById(R.id.textviewContraseñaEstudiante);
        TextView textviewPrograma = (TextView) listViewItem.findViewById(R.id.textviewProgramaEstudiante);
        TextView textviewSintomas = (TextView) listViewItem.findViewById(R.id.textviewSintomasEstudiante);

        //getting user at position
        Estudiante Estudiante = Estudiantes.get(position);

        textViewIdEstudiante.setText(Estudiante.getIdestudiante());
        //set user name
        textViewName.setText(Estudiante.getNombreestudiante());
        textviewApellido.setText(Estudiante.getApellidoestudiante());
        textviewCorreo.setText(Estudiante.getCorreoestudiante());
        textviewContraseña.setText(Estudiante.getContraseñaestudiante());
        textviewPrograma.setText(Estudiante.getProgramaestudiante());
        textviewSintomas.setText(Estudiante.getSintomasestudiante());

        return listViewItem;
    }
}
