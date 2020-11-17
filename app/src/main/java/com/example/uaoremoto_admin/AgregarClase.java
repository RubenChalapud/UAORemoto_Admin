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

public class AgregarClase extends AppCompatActivity {
    private EditText id, modo, asistencia, fecha, idcurso, idestudiante;
    private Button btnAgregarC, btnRegresar;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_clase);

        id = (EditText) findViewById(R.id.aTextIdCla);
        modo = (EditText) findViewById(R.id.aTextModoCla);
        asistencia = (EditText) findViewById(R.id.aTextAsisCla);
        fecha = (EditText) findViewById(R.id.aTextFecCla);
        idcurso = (EditText) findViewById(R.id.aTextIdcurCla);
        idestudiante = (EditText) findViewById(R.id.aTextIdesCla);

        btnAgregarC = (Button) findViewById(R.id.buttonAgregarProfesor);
        btnRegresar = (Button) findViewById(R.id.buttonReturnProfesor);

        //  referenciamos datos de firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Clases");

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(AgregarClase.this, AdminClases.class);
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
        String idClas = id.getText().toString().trim();
        String mod = modo.getText().toString().trim();
        String asi = asistencia.getText().toString().trim();
        String fec = fecha.getText().toString().trim();
        String idc = idcurso.getText().toString().trim();
        String ide = idestudiante.getText().toString().trim();

        //Valido que no esten vacios los campos

        if (!TextUtils.isEmpty(idClas)) {
            if (!TextUtils.isEmpty(mod)) {
                if (!TextUtils.isEmpty(asi)) {
                    if(!TextUtils.isEmpty(fec)){
                        if(!TextUtils.isEmpty(idc)){
                            if(!TextUtils.isEmpty(ide)){
                                //se crea la llave de registro
                                String idf = databaseReference.push().getKey();
                                // se crea un objeto del tipo usuario
                                Clase Clase = new Clase(idf, mod, asi, fec, idc, ide);
                                //Se guarda en Firebase
                                databaseReference.child(idf).setValue(Clase);
                                // Seteamos los campos
                                id.setText("");
                                modo.setText("");
                                asistencia.setText("");
                                fecha.setText("");
                                idcurso.setText("");
                                idestudiante.setText("");

                                Toast.makeText(getApplicationContext(), "Clase agregada", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Por favor ingrese un Id de Estudiante", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Por favor ingrese un Id de Curso", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Por favor ingrese Fecha", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Por favor ingrese Asistencia", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Por favor ingrese un Modo", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Por favor ingrese un Id", Toast.LENGTH_LONG).show();
        }

    }
}