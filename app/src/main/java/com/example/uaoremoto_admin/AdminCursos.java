package com.example.uaoremoto_admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

import java.util.ArrayList;
import java.util.List;

public class AdminCursos extends AppCompatActivity {
    //Inicializar menu
    DrawerLayout drawerLayout;

    //Lista de cursos
    ListView listViewCursos;
    private Button btnAgregarCursos;


    //lista que almacena todos los usuarios de la base de datos de Firebase
    List<Curso> Cursos;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cursos);

        //Menu
        drawerLayout = findViewById(R.id.drawer_layout_cursos);

        btnAgregarCursos = (Button) findViewById(R.id.btnAgregarCurso);

        btnAgregarCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(AdminCursos.this, AgregarCurso.class);
                startActivity(intencion);
            }
        });


        //  Metodo que encuentra por id
        findViews();

        //  Metodo que valida con clic los item de la lista para desplegar Modal
        initListner();
    }

    //  Metodo que encuentra por id
    private void findViews() {   //  Metodo que encuentra por id
        listViewCursos = (ListView) findViewById(R.id.listViewCursos);

        //  referenciamos datos de firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Cursos");

        // listado de objetos almacenados (usuarios creados)
        Cursos = new ArrayList<>();
    }

    // metodo que valida el evento clic para ingresar cursos
    private void initListner() {  // metodo que valida el evento clic para ingresar usuarios
        //  Al dar clic a un item de la lista
        listViewCursos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Curso Curso = Cursos.get(i); // validamos por id el item de la lista
                CallUpdateAndDeleteDialog(Curso.getIdcurso(), Curso.getNombrecurso(), Curso.getNumestudiantes(), Curso.getHorariocurso(), Curso.getIdprofesor(), Curso.getIdaula()); // llamamos a un modal que despliega form para editar o eliminar
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
                Cursos.clear();

                // obtenemos listado de datos
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //obtenemos los usuarios de la consola de Firebase
                    Curso Curso = postSnapshot.getValue(Curso.class);
                    // agregamos usuarios a la lista
                    Cursos.add(Curso);
                }
                //Creamos un adaptador para mostrar los usuarios de la lista
                CursoList UserAdapter = new CursoList(AdminCursos.this, Cursos);
                //Setiamos al adaptador el listado a desplegar
                listViewCursos.setAdapter(UserAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Modal con los datos a modificar y eliminar
    private void CallUpdateAndDeleteDialog(final String idcurso, String nombrecurso, String numestudiantes, String horariocurso, String idprofesor, String idaula) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_cursos, null);
        dialogBuilder.setView(dialogView);

        //Definición de interfaces del layout update_dialog
        final EditText updateTextname = (EditText) dialogView.findViewById(R.id.updateTextname);
        final EditText updateTextnumestudiantes = (EditText) dialogView.findViewById(R.id.updateTextnumestudiantes);
        final EditText updateTextHorario = (EditText) dialogView.findViewById(R.id.updateTexthorario);
        final EditText updateTextidprofe = (EditText) dialogView.findViewById(R.id.updateTextidprofesor);
        final EditText updateTextidaula = (EditText) dialogView.findViewById(R.id.updateTextidaula);

        updateTextname.setText(nombrecurso);
        updateTextnumestudiantes.setText(numestudiantes);
        updateTextHorario.setText(horariocurso);
        updateTextidprofe.setText(idprofesor);
        updateTextidaula.setText(idaula);

        //Botones
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateUser);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteUser);

        // seteo dialog title para el username
        dialogBuilder.setTitle(nombrecurso);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        // Cuando presiono el boton actualizar
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = updateTextname.getText().toString().trim(); // capturo los datos
                String numestudiantes = updateTextnumestudiantes.getText().toString().trim();
                String horario = updateTextHorario.getText().toString().trim();
                String idprofe = updateTextidprofe.getText().toString().trim();
                String idaula = updateTextidaula.getText().toString().trim();
                //valido que los campos tenga información

                if (!TextUtils.isEmpty(name)) {
                    if (!TextUtils.isEmpty(numestudiantes)) {
                        if (!TextUtils.isEmpty(horario)) {
                            if(!TextUtils.isEmpty(idprofe)){
                                if(!TextUtils.isEmpty(idaula)){
                                    //Method for update data
                                    updateUser(idcurso, name, numestudiantes, horario, idprofe, idaula); //llamo al metodo que  actualiza los datos en Firebase
                                    b.dismiss(); // cierro la ventana
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
                deleteUser(idcurso);
                b.dismiss();
            }
        });
    }


    private boolean updateUser(String idcurso, String name, String numestudiantes, String horario, String idprofe, String idaula) {
            //obtengo de Firebase el usuario especifico a modificar
            DatabaseReference UpdateReference = FirebaseDatabase.getInstance().getReference("Cursos").child(idcurso);
            Curso Curso = new Curso(idcurso, name, numestudiantes, horario, idprofe, idaula);
            //Actualizo usuarios en la colección de firebase
            UpdateReference.setValue(Curso);
            Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
            return true;
    }

    private boolean deleteUser(String idcurso) {
        //obtengo de Firebase el usuario especifico a ELIMINAR
        DatabaseReference DeleteReference = FirebaseDatabase.getInstance().getReference("Cursos").child(idcurso);
        //Borro el usuario de Firebase
        DeleteReference.removeValue();
        Toast.makeText(getApplicationContext(), "Curso eliminado", Toast.LENGTH_LONG).show();
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
        Intent i = new Intent(AdminCursos.this, InicioPortalAdmin.class);
        startActivity(i);
    }

    private void ClickAgregarCurso(View view){
        Intent i = new Intent(AdminCursos.this, AgregarCurso.class);
        startActivity(i);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    public void ClickSalir(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminCursos.this);
        builder.setTitle("Salir");
        builder.setMessage("¿Deseas salir de UAO Remoto?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AdminCursos.this.finishAffinity();
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