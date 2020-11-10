package com.example.uaoremoto_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InicioPortalAdmin extends AppCompatActivity {
    private Button btnCursos, btnClases, btnProfesor, btnAulas, btnestudiantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_portal_admin);

        btnCursos = (Button) findViewById(R.id.btnCursos);
        btnClases = (Button) findViewById(R.id.btnClases);
        btnProfesor = (Button) findViewById(R.id.btnProfesores);
        btnAulas = (Button) findViewById(R.id.btnAulas);
        btnestudiantes = (Button) findViewById(R.id.btnEstudiantes);

        btnCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InicioPortalAdmin.this, AdminCursos.class);
                startActivity(i);
            }
        });

        btnProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InicioPortalAdmin.this, AdminProfesores.class);
                startActivity(i);
            }
        });

        btnestudiantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InicioPortalAdmin.this, AdminEstudiantes.class);
                startActivity(i);
            }
        });

        btnAulas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InicioPortalAdmin.this, AdminAulas.class);
                startActivity(i);
            }
        });

    }
}