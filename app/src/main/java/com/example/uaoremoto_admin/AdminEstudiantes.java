package com.example.uaoremoto_admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminEstudiantes extends AppCompatActivity {
    //Inicializar menu
    DrawerLayout drawerLayout;

    //Lista de estudiantes
    ListView listViewEstudiantes;
    private Button btnAgregarEstudiante;

    //lista que almacena todos los usuarios de la base de datos de Firebase
    List<Estudiante> Estudiantes;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_estudiantes);

        //Menu
        drawerLayout = findViewById(R.id.drawer_layout_estudiantes);

        btnAgregarEstudiante = (Button) findViewById(R.id.btnAgregarEstudiante);

        btnAgregarEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(AdminEstudiantes.this, AgregarEstudiante.class);
                startActivity(intencion);
            }
        });

        //  Metodo que encuentra
        findViews();

        //  Metodo que valida con clic los item de la lista para desplegar Modal
        initListner();
    }

    private void initListner() {
        //  Al dar clic a un item de la lista
        listViewEstudiantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Estudiante Estudiante = Estudiantes.get(i); // validamos por id el item de la lista
                //String idestudiante, String nombreestudiante, String apellidoestudiante, String correoestudianteestudiante, String contraseñaestudiante, String programaestudiante, String sintomasestudiante
                CallUpdateAndDeleteDialog(Estudiante.getIdestudiante(), Estudiante.getNombreestudiante(), Estudiante.getApellidoestudiante(), Estudiante.getCorreoestudiante(), Estudiante.getContraseñaestudiante(), Estudiante.getProgramaestudiante(), Estudiante.getSintomasestudiante()); // llamamos a un modal que despliega form para editar o eliminar
            }
        });
    }

    private void findViews() {
        listViewEstudiantes = (ListView) findViewById(R.id.listViewEstudiantes);

        //  referenciamos datos de firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Estudiantes");

        // listado de objetos almacenados (usuarios creados)
        Estudiantes = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Borramos la lista previa de Cursos
                Estudiantes.clear();

                // obtenemos listado de datos
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //obtenemos los usuarios de la consola de Firebase
                    Estudiante Estudiante = postSnapshot.getValue(Estudiante.class);
                    // agregamos usuarios a la lista
                    Estudiantes.add(Estudiante);
                }
                //Creamos un adaptador para mostrar los usuarios de la lista
                EstudianteList UserAdapter = new EstudianteList(AdminEstudiantes.this, Estudiantes);
                //Setiamos al adaptador el listado a desplegar
                listViewEstudiantes.setAdapter(UserAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void CallUpdateAndDeleteDialog(final String idestudiante, String nombreestudiante, String apellidoestudiante, String correoestudiante, String contraseñaestudiante, String programaestudiante, String sintomasestudiante) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_estudiantes, null);
        dialogBuilder.setView(dialogView);

        //Definición de interfaces del layout update_dialog
        final EditText updateTextId = (EditText) dialogView.findViewById(R.id.updateTextIdEst);
        final EditText updateTextname = (EditText) dialogView.findViewById(R.id.updateTextNomEst);
        final EditText updateTextape = (EditText) dialogView.findViewById(R.id.updateTextApeEst);
        final EditText updateTextcor = (EditText) dialogView.findViewById(R.id.updateTextCorEst);
        final EditText updateTextcontra = (EditText) dialogView.findViewById(R.id.updateTextContraEst);
        final EditText updateTextprog = (EditText) dialogView.findViewById(R.id.updateTextProEst);
        final EditText updateTextsin = (EditText) dialogView.findViewById(R.id.updateTextSinEst);

        updateTextId.setText(idestudiante);
        updateTextname.setText(nombreestudiante);
        updateTextape.setText(apellidoestudiante);
        updateTextcor.setText(correoestudiante);
        updateTextcontra.setText(contraseñaestudiante);
        updateTextprog.setText(programaestudiante);
        updateTextsin.setText(sintomasestudiante);

        //Botones
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateUser);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteUser);

        // seteo dialog title para el username
        dialogBuilder.setTitle(idestudiante);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        // Cuando presiono el boton actualizar
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = updateTextId.getText().toString().trim();
                String name = updateTextname.getText().toString().trim(); // capturo los datos
                String ape = updateTextape.getText().toString().trim();
                String cor = updateTextcor.getText().toString().trim();
                String contra = updateTextcontra.getText().toString().trim();
                String prog = updateTextprog.getText().toString().trim();
                String sinto = updateTextsin.getText().toString().trim();
                //valido que los campos tenga información

                if (!TextUtils.isEmpty(id)) {
                    if (!TextUtils.isEmpty(name)) {
                        if (!TextUtils.isEmpty(ape)) {
                            if(!TextUtils.isEmpty(cor)){
                                if(!TextUtils.isEmpty(contra)){
                                    if(!TextUtils.isEmpty(prog)){
                                        if(!TextUtils.isEmpty(sinto)){
                                            //Method for update data
                                            updateUser(id, name, ape, cor, contra, prog, sinto); //llamo al metodo que  actualiza los datos en Firebase
                                            b.dismiss(); // cierro la ventana
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        });

        // Cuando presiono eliminar
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Llamo al metodo que borra datos en firebase
                deleteUser(idestudiante);
                b.dismiss();
            }
        });
    }

    private boolean deleteUser(String idestudiante) {
        //obtengo de Firebase el usuario especifico a ELIMINAR
        DatabaseReference DeleteReference = FirebaseDatabase.getInstance().getReference("Estudiantes").child(idestudiante);
        //Borro el usuario de Firebase
        DeleteReference.removeValue();
        Toast.makeText(getApplicationContext(), "Estudiante eliminado", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean updateUser(String id, String name, String ape, String cor, String contra, String prog, String sinto) {
        //obtengo de Firebase el usuario especifico a modificar
        DatabaseReference UpdateReference = FirebaseDatabase.getInstance().getReference("Estudiantes").child(id);
        Estudiante Estudiante = new Estudiante(id, name, ape, cor, contra, prog, sinto);
        //Actualizo usuarios en la colección de firebase
        UpdateReference.setValue(Estudiante);
        Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    //Metodos para Menu
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickInicio(View view){
        Intent i = new Intent(AdminEstudiantes.this, InicioPortalAdmin.class);
        startActivity(i);
    }

    private void ClickAgregarProfesor(View view){
        Intent i = new Intent(AdminEstudiantes.this, AgregarEstudiante.class);
        startActivity(i);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    public void ClickSalir(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminEstudiantes.this);
        builder.setTitle("Salir");
        builder.setMessage("¿Deseas salir de UAO Remoto?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AdminEstudiantes.this.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }


}