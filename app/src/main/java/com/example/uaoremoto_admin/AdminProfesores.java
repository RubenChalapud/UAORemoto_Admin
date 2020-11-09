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

public class AdminProfesores extends AppCompatActivity {
    //Inicializar menu
    DrawerLayout drawerLayout;

    //Lista de profesores
    ListView listViewProfesores;
    private Button btnAgregarProfesor;

    //lista que almacena todos los usuarios de la base de datos de Firebase
    List<Profesor> Profesores;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profesores);

        //Menu
        drawerLayout = findViewById(R.id.drawer_layout_profesores);

        btnAgregarProfesor = (Button) findViewById(R.id.btnAgregarProfesor);

        btnAgregarProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(AdminProfesores.this, AgregarProfesor.class);
                startActivity(intencion);
            }
        });

        //  Metodo que encuentra
        findViews();

        //  Metodo que valida con clic los item de la lista para desplegar Modal
        initListner();
    }

    private void findViews() {
        listViewProfesores = (ListView) findViewById(R.id.listViewProfesores);

        //  referenciamos datos de firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Profesores");

        // listado de objetos almacenados (usuarios creados)
        Profesores = new ArrayList<>();
    }

    private void initListner() {
        //  Al dar clic a un item de la lista
        listViewProfesores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Profesor Profesor = Profesores.get(i); // validamos por id el item de la lista
                //String idprofesor, String nombreprofesor, String apellidoprofesor, String correoprofesor, String contraseñaprofesor, String sintomasprofesor
                CallUpdateAndDeleteDialog(Profesor.getIdprofesor(), Profesor.getNombreprofesor(), Profesor.getApellidoprofesor(), Profesor.getCorreoprofesor(), Profesor.getContraseñaprofesor(), Profesor.getSintomasprofesor()); // llamamos a un modal que despliega form para editar o eliminar
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Borramos la lista previa de Cursos
                Profesores.clear();

                // obtenemos listado de datos
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //obtenemos los usuarios de la consola de Firebase
                    Profesor Profesor = postSnapshot.getValue(Profesor.class);
                    // agregamos usuarios a la lista
                    Profesores.add(Profesor);
                }
                //Creamos un adaptador para mostrar los usuarios de la lista
                ProfesorList UserAdapter = new ProfesorList(AdminProfesores.this, Profesores);
                //Setiamos al adaptador el listado a desplegar
                listViewProfesores.setAdapter(UserAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void CallUpdateAndDeleteDialog(final String idprofesor, String nombreprofesor, String apellidoprofesor, String correoprofesor, String contraseñaprofesor, String sintomasprofesor) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_profesores, null);
        dialogBuilder.setView(dialogView);

        //Definición de interfaces del layout update_dialog
        final EditText updateTextId = (EditText) dialogView.findViewById(R.id.updateTextIdPro);
        final EditText updateTextname = (EditText) dialogView.findViewById(R.id.updateTextnamePro);
        final EditText updateTextape = (EditText) dialogView.findViewById(R.id.updateTextApePro);
        final EditText updateTextcor = (EditText) dialogView.findViewById(R.id.updateTextCorPro);
        final EditText updateTextcontra = (EditText) dialogView.findViewById(R.id.updateTextContraPro);
        final EditText updateTextsin = (EditText) dialogView.findViewById(R.id.updateTextSinPro);

        updateTextId.setText(idprofesor);
        updateTextname.setText(nombreprofesor);
        updateTextape.setText(apellidoprofesor);
        updateTextcor.setText(correoprofesor);
        updateTextcontra.setText(contraseñaprofesor);
        updateTextsin.setText(sintomasprofesor);

        //Botones
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateUser);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteUser);

        // seteo dialog title para el username
        dialogBuilder.setTitle(idprofesor);
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
                String sinto = updateTextsin.getText().toString().trim();
                //valido que los campos tenga información

                if (!TextUtils.isEmpty(id)) {
                    if (!TextUtils.isEmpty(name)) {
                        if (!TextUtils.isEmpty(ape)) {
                            if(!TextUtils.isEmpty(cor)){
                                if(!TextUtils.isEmpty(contra)){
                                    if(!TextUtils.isEmpty(sinto)){
                                        //Method for update data
                                        updateUser(id, name, ape, cor, contra, sinto); //llamo al metodo que  actualiza los datos en Firebase
                                        b.dismiss(); // cierro la ventana
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
                deleteUser(idprofesor);
                b.dismiss();
            }
        });
    }

    private Boolean deleteUser(String idprofesor) {
        //obtengo de Firebase el usuario especifico a ELIMINAR
        DatabaseReference DeleteReference = FirebaseDatabase.getInstance().getReference("Profesores").child(idprofesor);
        //Borro el usuario de Firebase
        DeleteReference.removeValue();
        Toast.makeText(getApplicationContext(), "Profesor eliminado", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean updateUser(String id, String name, String ape, String cor, String contra, String sinto) {
        //obtengo de Firebase el usuario especifico a modificar
        DatabaseReference UpdateReference = FirebaseDatabase.getInstance().getReference("Profesores").child(id);
        Profesor Profesor = new Profesor(id, name, ape, cor, contra, sinto);
        //Actualizo usuarios en la colección de firebase
        UpdateReference.setValue(Profesor);
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
        Intent i = new Intent(AdminProfesores.this, InicioPortalAdmin.class);
        startActivity(i);
    }

    private void ClickAgregarProfesor(View view){
        Intent i = new Intent(AdminProfesores.this, AgregarProfesor.class);
        startActivity(i);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    public void ClickSalir(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminProfesores.this);
        builder.setTitle("Salir");
        builder.setMessage("¿Deseas salir de UAO Remoto?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AdminProfesores.this.finishAffinity();
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