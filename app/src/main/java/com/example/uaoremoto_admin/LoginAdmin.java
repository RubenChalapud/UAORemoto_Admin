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

public class LoginAdmin extends AppCompatActivity implements View.OnClickListener {
    private EditText textUser, textContra;
    private Button btnIngresar;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        firebaseAuth = FirebaseAuth.getInstance();

        textUser = (EditText) findViewById(R.id.editTextLogD);
        textContra = (EditText) findViewById(R.id.editTextPassD);

        btnIngresar = (Button) findViewById(R.id.btnLogD);

        progressDialog = new ProgressDialog(this);

        btnIngresar.setOnClickListener(this);
    }

    private void loguearUsuario() {
        //Obtenemos el email y la contraseña desde las cajas de texto
        final String email = textUser.getText().toString().trim();
        String password = textContra.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un Usuario", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();

        //loguear usuario
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            int pos = email.indexOf("@");
                            String user = email.substring(0, pos);
                            if(user.equals("admin.uao")){
                                Toast.makeText(LoginAdmin.this, "Bienvenido: " + textUser.getText(), Toast.LENGTH_LONG).show();
                                Intent intencion = new Intent(getApplication(), InicioPortalAdmin.class);
                                startActivity(intencion);
                            }else{
                                Toast.makeText(LoginAdmin.this, "El usuario no tiene autorizado el ingreso", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(LoginAdmin.this, "El usuario o contraseña es erroneo", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginAdmin.this, "El usuario o contraseña es erroneo", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogD:
                //Invocamos al método:
                loguearUsuario();
                break;
        }
    }
}
