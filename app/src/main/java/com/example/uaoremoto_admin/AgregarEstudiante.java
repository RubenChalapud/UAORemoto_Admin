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

public class AgregarEstudiante extends AppCompatActivity {
    private EditText id, nombre, apellido, correo, contraseña, programa, sintomas;
    private Button btnAgregarE, btnRegresar;

    DatabaseReference databaseReference;

    private ProgressDialog progressDialog;


    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_estudiante);

        id = (EditText) findViewById(R.id.aTextIdEst);
        nombre = (EditText) findViewById(R.id.aTextNombreEst);
        apellido = (EditText) findViewById(R.id.aTextApeEst);
        correo = (EditText) findViewById(R.id.aTextCorEst);
        contraseña = (EditText) findViewById(R.id.aTextContraEst);
        programa = (EditText) findViewById(R.id.aTextProEst);
        sintomas = (EditText) findViewById(R.id.aTextSinEst);

        btnAgregarE = (Button) findViewById(R.id.buttonAgregarProfesor);
        btnRegresar = (Button) findViewById(R.id.buttonReturnProfesor);

        //  referenciamos datos de firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Estudiantes");

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(AgregarEstudiante.this, AdminEstudiantes.class);
                startActivity(intencion);
            }
        });

        btnAgregarE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    private void addUser() {
        //capturo los datos que deseo ingresar
        String idEst = id.getText().toString().trim();
        String name = nombre.getText().toString().trim();
        String ape = apellido.getText().toString().trim();
        String cor = correo.getText().toString().trim();
        String contra = contraseña.getText().toString().trim();
        String pro = programa.getText().toString().trim();
        String sin = sintomas.getText().toString().trim();

        //Valido que no esten vacios los campos

        if (!TextUtils.isEmpty(idEst)) {
            if (!TextUtils.isEmpty(name)) {
                if (!TextUtils.isEmpty(ape)) {
                    if(!TextUtils.isEmpty(cor)){
                        if(!TextUtils.isEmpty(contra)){
                            if(!TextUtils.isEmpty(pro)){
                                if(!TextUtils.isEmpty(sin)){
                                    //se crea la llave de registro
                                    //String id = databaseReference.push().getKey();
                                    // se crea un objeto del tipo usuario
                                    Estudiante Estudiante = new Estudiante(idEst, name, ape, cor, contra, pro, sin);
                                    //Se guarda en Firebase
                                    databaseReference.child(idEst).setValue(Estudiante);
                                    //Se realiza metodo para la autenticacion del usuario
                                    registrarUsuario();
                                    // Seteamos los campos
                                    id.setText("");
                                    nombre.setText("");
                                    apellido.setText("");
                                    correo.setText("");
                                    contraseña.setText("");
                                    programa.setText("");
                                    sintomas.setText("");

                                    Toast.makeText(getApplicationContext(), "Estudiante agregado", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Por favor ingrese Sintomas", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Por favor ingrese Programa", Toast.LENGTH_LONG).show();
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
            Toast.makeText(getApplicationContext(), "Por favor ingrese un Codigo", Toast.LENGTH_LONG).show();
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

                            Toast.makeText(AgregarEstudiante.this, "Se ha registrado el usuario", Toast.LENGTH_LONG).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(AgregarEstudiante.this, "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AgregarEstudiante.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });

    }
}