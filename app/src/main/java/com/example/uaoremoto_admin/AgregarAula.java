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

public class AgregarAula extends AppCompatActivity {
    private EditText id, nombre, latitud, longitud;
    private Button btnAgregarA, btnRegresar;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_aula);

        id = (EditText) findViewById(R.id.aTextIdAul);
        nombre = (EditText) findViewById(R.id.aTextNombreAul);
        latitud = (EditText) findViewById(R.id.aTextLatAul);
        longitud = (EditText) findViewById(R.id.aTextLonAul);

        btnAgregarA = (Button) findViewById(R.id.buttonAgregarAula);
        btnRegresar = (Button) findViewById(R.id.buttonReturnAula);

        //  referenciamos datos de firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Aulas");

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(AgregarAula.this, AdminAulas.class);
                startActivity(intencion);
            }
        });

        btnAgregarA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    private void addUser() {
        //capturo los datos que deseo ingresar
        String idAul = id.getText().toString().trim();
        String name = nombre.getText().toString().trim();
        String lat = latitud.getText().toString().trim();
        String lon = longitud.getText().toString().trim();

        //Valido que no esten vacios los campos

        if (!TextUtils.isEmpty(idAul)) {
            if (!TextUtils.isEmpty(name)) {
                if (!TextUtils.isEmpty(lat)) {
                    if(!TextUtils.isEmpty(lon)){
                        //se crea la llave de registro
                        //String id = databaseReference.push().getKey();
                        // se crea un objeto del tipo usuario
                        Aula Aula = new Aula(idAul, name, lat, lon);
                        //Se guarda en Firebase
                        databaseReference.child(idAul).setValue(Aula);
                        // Seteamos los campos
                        id.setText("");
                        nombre.setText("");
                        latitud.setText("");
                        longitud.setText("");

                        Toast.makeText(getApplicationContext(), "Aula agregada", Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(getApplicationContext(), "Por favor ingrese Longitud", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Por favor ingrese Latitud", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Por favor ingrese Nombre", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Por favor ingrese un Id", Toast.LENGTH_LONG).show();
        }

    }
}