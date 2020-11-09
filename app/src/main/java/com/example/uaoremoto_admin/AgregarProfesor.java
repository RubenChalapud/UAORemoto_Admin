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

public class AgregarProfesor extends AppCompatActivity {
    private EditText id, nombre, apellido, correo, contraseña, sintomas;
    private Button btnAgregarP, btnRegresar;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_profesor);

        id = (EditText) findViewById(R.id.aTextIdPro);
        nombre = (EditText) findViewById(R.id.aTextNombrePro);
        apellido = (EditText) findViewById(R.id.aTextApePro);
        correo = (EditText) findViewById(R.id.aTextCorPro);
        contraseña = (EditText) findViewById(R.id.aTextContraPro);
        sintomas = (EditText) findViewById(R.id.aTextSinPro);

        btnAgregarP = (Button) findViewById(R.id.buttonAgregarProfesor);
        btnRegresar = (Button) findViewById(R.id.buttonReturnProfesor);

        //  referenciamos datos de firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Profesores");

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(AgregarProfesor.this, AdminProfesores.class);
                startActivity(intencion);
            }
        });

        btnAgregarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    private void addUser() {
        //capturo los datos que deseo ingresar
        String idPro = id.getText().toString().trim();
        String name = nombre.getText().toString().trim();
        String ape = apellido.getText().toString().trim();
        String cor = correo.getText().toString().trim();
        String contra = contraseña.getText().toString().trim();
        String sin = sintomas.getText().toString().trim();

        //Valido que no esten vacios los campos

        if (!TextUtils.isEmpty(idPro)) {
            if (!TextUtils.isEmpty(name)) {
                if (!TextUtils.isEmpty(ape)) {
                    if(!TextUtils.isEmpty(cor)){
                        if(!TextUtils.isEmpty(contra)){
                            if(!TextUtils.isEmpty(sin)){
                                //se crea la llave de registro
                                //String id = databaseReference.push().getKey();
                                // se crea un objeto del tipo usuario
                                Profesor Profesor = new Profesor(idPro, name, ape, cor, contra, sin);
                                //Se guarda en Firebase
                                databaseReference.child(idPro).setValue(Profesor);
                                // Seteamos los campos
                                id.setText("");
                                nombre.setText("");
                                apellido.setText("");
                                correo.setText("");
                                contraseña.setText("");
                                sintomas.setText("");
                                Toast.makeText(getApplicationContext(), "Profesor agregado", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Por favor ingrese Sintomas", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Por favor ingrese una Contraseña", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Por favor ingrese un Correo", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Por favor ingrese un Apellido", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Por favor ingrese un Nombre", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Por favor ingrese un Id", Toast.LENGTH_LONG).show();
        }

    }
}