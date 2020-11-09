package com.example.uaoremoto_admin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ProfesorList extends ArrayAdapter<Profesor> {

    private Activity context;

    //Lista de profesores
    List<Profesor> Profesores;

    public ProfesorList(Activity context, List<Profesor> Profesores){
        super(context, R.layout.layout_profesor_list, Profesores);
        this.context = context;
        this.Profesores = Profesores;
    }

    // clase adpatadora para desplegar el listado de elementos almacenados en Firebase
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_profesor_list, null, true);
        //initialize String idprofesor, String nombreprofesor, String apellidoprofesor, String correoprofesor, String contraseñaprofesor, String sintomasprofesor
        TextView textViewIdProfesor = (TextView) listViewItem.findViewById(R.id.textViewIdProfesor);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewNombreProfesor);
        TextView textviewApellido = (TextView) listViewItem.findViewById(R.id.textviewApellidoProfesor);
        TextView textviewCorreo = (TextView) listViewItem.findViewById(R.id.textviewCorreoProfesor);
        TextView textviewContraseña = (TextView) listViewItem.findViewById(R.id.textviewContraseñaProfesor);
        TextView textviewSintomas = (TextView) listViewItem.findViewById(R.id.textviewSintomasProfesor);

        //getting user at position
        Profesor Profesor = Profesores.get(position);

        textViewIdProfesor.setText(Profesor.getIdprofesor());
        //set user name
        textViewName.setText(Profesor.getNombreprofesor());
        textviewApellido.setText(Profesor.getApellidoprofesor());
        textviewCorreo.setText(Profesor.getCorreoprofesor());
        textviewContraseña.setText(Profesor.getContraseñaprofesor());
        textviewSintomas.setText(Profesor.getSintomasprofesor());

        return listViewItem;
    }
}
