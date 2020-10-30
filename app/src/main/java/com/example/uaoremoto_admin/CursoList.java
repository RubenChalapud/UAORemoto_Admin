package com.example.uaoremoto_admin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CursoList extends ArrayAdapter<Curso> {

    private Activity context;

    //Lista de cursos
    List<Curso> Cursos;

    public CursoList(Activity context, List<Curso> Cursos){
        super(context, R.layout.layout_curso_list, Cursos);
        this.context = context;
        this.Cursos = Cursos;
    }

    // clase adpatadora para desplegar el listado de elementos almacenados en Firebase
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_curso_list, null, true);
        //initialize
        TextView textViewIdCurso = (TextView) listViewItem.findViewById(R.id.textViewIdCurso);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewNameCurso);
        TextView textviewnumberEst = (TextView) listViewItem.findViewById(R.id.textviewnumEstudiCurso);
        TextView textviewhorario = (TextView) listViewItem.findViewById(R.id.textviewhorarioCurso);
        TextView textviewidprofesor = (TextView) listViewItem.findViewById(R.id.textviewIdprofesorCurso);
        TextView textviewidaula = (TextView) listViewItem.findViewById(R.id.textviewIdaulaCurso);

        //getting user at position
        Curso Curso = Cursos.get(position);

        textViewIdCurso.setText(Curso.getIdcurso());
        //set user name
        textViewName.setText(Curso.getNombrecurso());
        textviewnumberEst.setText(Curso.getNumestudiantes());
        textviewhorario.setText(Curso.getHorariocurso());
        textviewidprofesor.setText(Curso.getIdprofesor());
        textviewidaula.setText(Curso.getIdaula());

        return listViewItem;
    }



}
