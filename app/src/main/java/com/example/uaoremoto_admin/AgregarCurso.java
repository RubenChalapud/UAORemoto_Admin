package com.example.uaoremoto_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AgregarCurso extends AppCompatActivity {
    private EditText nombre, numeroestudiantes, horario, idprofesor, idaula;
    private Button btnAgregarC, btnRegresar;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_curso);

        nombre = (EditText) findViewById(R.id.aTextname);
        numeroestudiantes = (EditText) findViewById(R.id.aTextnumestudiantes);
        horario = (EditText) findViewById(R.id.aTexthorario);
        idprofesor = (EditText) findViewById(R.id.aTextidprofesor);
        idaula = (EditText) findViewById(R.id.aTextidaula);

        btnAgregarC = (Button) findViewById(R.id.buttonAgregarCurso);
        btnRegresar = (Button) findViewById(R.id.buttonReturnCursos);

        //  referenciamos datos de firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Cursos");

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(AgregarCurso.this, AdminCursos.class);
                startActivity(intencion);
            }
        });

        btnAgregarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    private void addUser() {
        //capturo los datos que deseo ingresar
        String name = nombre.getText().toString().trim();
        String numestudiantes = numeroestudiantes.getText().toString().trim();
        String horarioC = horario.getText().toString().trim();
        String idprofe = idprofesor.getText().toString().trim();
        String idaulaC = idaula.getText().toString().trim();

        //Valido que no esten vacios los campos

        if (!TextUtils.isEmpty(name)) {
            if (!TextUtils.isEmpty(numestudiantes)) {
                if (!TextUtils.isEmpty(horarioC)) {
                    if(!TextUtils.isEmpty(idprofe)){
                        if(!TextUtils.isEmpty(idaulaC)){

                            //se crea la llave de registro
                            String id = databaseReference.push().getKey();
                            // se crea un objeto del tipo usuario
                            Clase Clase = new Clase(id, name, numestudiantes, horarioC, idprofe, idaulaC);
                            //Se guarda en Firebase
                            databaseReference.child(id).setValue(Clase);
                            // Seteamos los campos
                            nombre.setText("");
                            numeroestudiantes.setText("");
                            horario.setText("");
                            idprofesor.setText("");
                            idaula.setText("");

                            Toast.makeText(getApplicationContext(), "Clase agregada", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(getApplicationContext(), "Por favor ingrese un id de Aula", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Por favor ingrese un id de Profesor", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Por favor ingrese un Horario", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Por favor ingrese un Numero", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Por favor ingrese un Nombre", Toast.LENGTH_LONG).show();
        }

    }
}