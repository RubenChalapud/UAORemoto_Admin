package com.example.uaoremoto_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AgregarProfesor extends AppCompatActivity {
    private EditText id, nombre, apellido, correo, contraseña, sintomas;
    private Button btnAgregarP, btnRegresar;

    DatabaseReference databaseReference;

    private ProgressDialog progressDialog;


    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

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

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

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
                                registrarUsuario();
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

    private void registrarUsuario() {

        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = correo.getText().toString().trim();
        String password = contraseña.getText().toString().trim();


        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        //registramos un nuevo usuario
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            Toast.makeText(AgregarProfesor.this, "Se ha registrado el usuario", Toast.LENGTH_LONG).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(AgregarProfesor.this, "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AgregarProfesor.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });

    }
}