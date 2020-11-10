package com.example.uaoremoto_admin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ClaseList extends ArrayAdapter<Clase> {
    private Activity context;

    //Lista de clases
    List<Clase> Clases;

    public ClaseList(Activity context, List<Clase> Clases){
        super(context, R.layout.layout_clase_list, Clases);
        this.context = context;
        this.Clases = Clases;
    }

    // clase adpatadora para desplegar el listado de elementos almacenados en Firebase
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_clase_list, null, true);

        //String idclase, String modoclase, String asistenciaclase, String fechaclase, String idcurso, String idestudiante
        TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewIdClase);
        TextView textViewModo = (TextView) listViewItem.findViewById(R.id.textViewModoClase);
        TextView textviewAsistencia = (TextView) listViewItem.findViewById(R.id.textviewAsistenciaClase);
        TextView textviewFecha = (TextView) listViewItem.findViewById(R.id.textviewFechaClase);
        TextView textviewIdcurso = (TextView) listViewItem.findViewById(R.id.textviewIdcursoClase);
        TextView textviewIdestudiante = (TextView) listViewItem.findViewById(R.id.textviewIdestudianteClase);


        //getting user at position
        Clase Clase = Clases.get(position);

        textViewId.setText(Clase.getIdclase());
        //set user name
        textViewModo.setText(Clase.getModoclase());
        textviewAsistencia.setText(Clase.getAsistenciaclase());
        textviewFecha.setText(Clase.getFechaclase());
        textviewIdcurso.setText(Clase.getIdcurso());
        textviewIdestudiante.setText(Clase.getIdestudiante());

        return listViewItem;
    }
}
