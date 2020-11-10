package com.example.uaoremoto_admin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AulaList extends ArrayAdapter<Aula> {
    private Activity context;

    //Lista de aulas
    List<Aula> Aulas;

    public AulaList(Activity context, List<Aula> Aulas){
        super(context, R.layout.layout_aula_list, Aulas);
        this.context = context;
        this.Aulas = Aulas;
    }

    // clase adpatadora para desplegar el listado de elementos almacenados en Firebase
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_aula_list, null, true);
        //String idaula, String nombreaula, String latitud, String longitud
        TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewIdAula);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewNombreAula);
        TextView textviewLatitud = (TextView) listViewItem.findViewById(R.id.textviewLatitudAula);
        TextView textviewLongitud = (TextView) listViewItem.findViewById(R.id.textviewLongitudAula);


        //getting user at position
        Aula Aula = Aulas.get(position);

        textViewId.setText(Aula.getIdaula());
        //set user name
        textViewName.setText(Aula.getNombreaula());
        textviewLatitud.setText(Aula.getLatitud());
        textviewLongitud.setText(Aula.getLongitud());

        return listViewItem;
    }
}
