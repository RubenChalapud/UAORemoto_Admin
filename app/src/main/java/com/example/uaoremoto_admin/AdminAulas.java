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

public class AdminAulas extends AppCompatActivity {
    //Inicializar menu
    DrawerLayout drawerLayout;

    //Lista de estudiantes
    ListView listViewAulas;
    private Button btnAgregarAulas;

    //lista que almacena todos los usuarios de la base de datos de Firebase
    List<Aula> Aulas;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_aulas);

        //Menu
        drawerLayout = findViewById(R.id.drawer_layout_aulas);

        btnAgregarAulas = (Button) findViewById(R.id.btnAgregarAula);

        btnAgregarAulas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(AdminAulas.this, AgregarAula.class);
                startActivity(intencion);
            }
        });

        //  Metodo que encuentra
        findViews();

        //  Metodo que valida con clic los item de la lista para desplegar Modal
        initListner();
    }

    private void findViews() {
        listViewAulas = (ListView) findViewById(R.id.listViewAulas);

        //  referenciamos datos de firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Aulas");

        // listado de objetos almacenados (usuarios creados)
        Aulas = new ArrayList<>();
    }

    private void initListner() {
        //  Al dar clic a un item de la lista
        listViewAulas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Aula Aula = Aulas.get(i); // validamos por id el item de la lista
                //String idaula, String nombreaula, String latitud, String longitud
                CallUpdateAndDeleteDialog(Aula.getIdaula(), Aula.getNombreaula(), Aula.getLatitud(), Aula.getLongitud()); // llamamos a un modal que despliega form para editar o eliminar
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
                Aulas.clear();

                // obtenemos listado de datos
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //obtenemos los usuarios de la consola de Firebase
                    Aula Aula = postSnapshot.getValue(Aula.class);
                    // agregamos usuarios a la lista
                    Aulas.add(Aula);
                }
                //Creamos un adaptador para mostrar los usuarios de la lista
                AulaList UserAdapter = new AulaList(AdminAulas.this, Aulas);
                //Setiamos al adaptador el listado a desplegar
                listViewAulas.setAdapter(UserAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void CallUpdateAndDeleteDialog(final String idaula, String nombreaula, String latitud, String longitud) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_aulas, null);
        dialogBuilder.setView(dialogView);

        //Definición de interfaces del layout update_dialog
        final EditText updateTextId = (EditText) dialogView.findViewById(R.id.updateTextIdAul);
        final EditText updateTextname = (EditText) dialogView.findViewById(R.id.updateTextNomAul);
        final EditText updateTextLat = (EditText) dialogView.findViewById(R.id.updateTextLatAul);
        final EditText updateTextLon = (EditText) dialogView.findViewById(R.id.updateTextLongAul);


        updateTextId.setText(idaula);
        updateTextname.setText(nombreaula);
        updateTextLat.setText(latitud);
        updateTextLon.setText(longitud);

        //Botones
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateUser);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteUser);

        // seteo dialog title para el username
        dialogBuilder.setTitle(idaula);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        // Cuando presiono el boton actualizar
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = updateTextId.getText().toString().trim();
                String name = updateTextname.getText().toString().trim(); // capturo los datos
                String lat = updateTextLat.getText().toString().trim();
                String lon = updateTextLon.getText().toString().trim();

                //valido que los campos tenga información

                if (!TextUtils.isEmpty(id)) {
                    if (!TextUtils.isEmpty(name)) {
                        if (!TextUtils.isEmpty(lat)) {
                            if(!TextUtils.isEmpty(lon)){
                                //Method for update data
                                updateUser(id, name, lat, lon); //llamo al metodo que  actualiza los datos en Firebase
                                b.dismiss(); // cierro la ventana
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
                deleteUser(idaula);
                b.dismiss();
            }
        });

    }

    private boolean deleteUser(String idaula) {
        //obtengo de Firebase el usuario especifico a ELIMINAR
        DatabaseReference DeleteReference = FirebaseDatabase.getInstance().getReference("Aulas").child(idaula);
        //Borro el usuario de Firebase
        DeleteReference.removeValue();
        Toast.makeText(getApplicationContext(), "Aula eliminada", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean updateUser(String id, String name, String lat, String lon) {
        //obtengo de Firebase el usuario especifico a modificar
        DatabaseReference UpdateReference = FirebaseDatabase.getInstance().getReference("Aulas").child(id);
        Aula Aula = new Aula(id, name, lat, lon);
        //Actualizo usuarios en la colección de firebase
        UpdateReference.setValue(Aula);
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
        Intent i = new Intent(AdminAulas.this, InicioPortalAdmin.class);
        startActivity(i);
    }

    private void ClickAgregarProfesor(View view){
        Intent i = new Intent(AdminAulas.this, AgregarAula.class);
        startActivity(i);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    public void ClickSalir(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminAulas.this);
        builder.setTitle("Salir");
        builder.setMessage("¿Deseas salir de UAO Remoto?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AdminAulas.this.finishAffinity();
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
