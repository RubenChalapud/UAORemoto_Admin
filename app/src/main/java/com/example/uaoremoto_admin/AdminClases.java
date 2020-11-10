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

public class AdminClases extends AppCompatActivity {
    //Inicializar menu
    DrawerLayout drawerLayout;

    //Lista de estudiantes
    ListView listViewClases;
    private Button btnAgregarClase;

    //lista que almacena todos los usuarios de la base de datos de Firebase
    List<Clase> Clases;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_clases);

        //Menu
        drawerLayout = findViewById(R.id.drawer_layout_clases);

        btnAgregarClase = (Button) findViewById(R.id.btnAgregarClases);

        btnAgregarClase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(AdminClases.this, AgregarClase.class);
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
        listViewClases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Clase Clase = Clases.get(i); // validamos por id el item de la lista
                //String idclase, String modoclase, String asistenciaclase, String fechaclase, String idcurso, String idestudiante
                CallUpdateAndDeleteDialog(Clase.getIdclase(), Clase.getModoclase(), Clase.getAsistenciaclase(), Clase.getFechaclase(), Clase.getIdcurso(), Clase.getIdestudiante()); // llamamos a un modal que despliega form para editar o eliminar
            }
        });
    }


    private void findViews() {
        listViewClases = (ListView) findViewById(R.id.listViewClases);

        //  referenciamos datos de firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Clases");

        // listado de objetos almacenados (usuarios creados)
        Clases = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Borramos la lista previa de Cursos
                Clases.clear();

                // obtenemos listado de datos
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //obtenemos los usuarios de la consola de Firebase
                    Clase Clase = postSnapshot.getValue(Clase.class);
                    // agregamos usuarios a la lista
                    Clases.add(Clase);
                }
                //Creamos un adaptador para mostrar los usuarios de la lista
                ClaseList UserAdapter = new ClaseList(AdminClases.this, Clases);
                //Setiamos al adaptador el listado a desplegar
                listViewClases.setAdapter(UserAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void CallUpdateAndDeleteDialog(final String idclase, String modoclase, String asistenciaclase, String fechaclase, String idcurso, String idestudiante) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_clases, null);
        dialogBuilder.setView(dialogView);

        //Definición de interfaces del layout update_dialog
        final EditText updateTextId = (EditText) dialogView.findViewById(R.id.updateTextIdClas);
        final EditText updateTextmodo = (EditText) dialogView.findViewById(R.id.updateTextModoClas);
        final EditText updateTextasis = (EditText) dialogView.findViewById(R.id.updateTextAsistenciaClas);
        final EditText updateTextfecha = (EditText) dialogView.findViewById(R.id.updateTextFechaClas);
        final EditText updateTextidcur = (EditText) dialogView.findViewById(R.id.updateTextIdCursoClas);
        final EditText updateTextidest = (EditText) dialogView.findViewById(R.id.updateTextIdEstClas);

        updateTextId.setText(idclase);
        updateTextmodo.setText(modoclase);
        updateTextasis.setText(asistenciaclase);
        updateTextfecha.setText(fechaclase);
        updateTextidcur.setText(idcurso);
        updateTextidest.setText(idestudiante);

        //Botones
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateUser);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteUser);

        // seteo dialog title para el username
        dialogBuilder.setTitle(idclase);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        // Cuando presiono el boton actualizar
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = updateTextId.getText().toString().trim();
                String mod = updateTextmodo.getText().toString().trim(); // capturo los datos
                String asi = updateTextasis.getText().toString().trim();
                String fec = updateTextfecha.getText().toString().trim();
                String idc = updateTextidcur.getText().toString().trim();
                String ide = updateTextidest.getText().toString().trim();
                //valido que los campos tenga información

                if (!TextUtils.isEmpty(id)) {
                    if (!TextUtils.isEmpty(mod)) {
                        if (!TextUtils.isEmpty(asi)) {
                            if(!TextUtils.isEmpty(fec)){
                                if(!TextUtils.isEmpty(idc)){
                                    if(!TextUtils.isEmpty(ide)){
                                        //Method for update data
                                        updateUser(id, mod, asi, fec, idc, ide); //llamo al metodo que  actualiza los datos en Firebase
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
                deleteUser(idclase);
                b.dismiss();
            }
        });

    }

    private boolean updateUser(String id, String mod, String asi, String fec, String idc, String ide) {
        //obtengo de Firebase el usuario especifico a modificar
        DatabaseReference UpdateReference = FirebaseDatabase.getInstance().getReference("Clases").child(id);
        Clase Clase = new Clase(id, mod, asi, fec, idc, ide);
        //Actualizo usuarios en la colección de firebase
        UpdateReference.setValue(Clase);
        Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteUser(String idclase) {
        //obtengo de Firebase el usuario especifico a ELIMINAR
        DatabaseReference DeleteReference = FirebaseDatabase.getInstance().getReference("Clases").child(idclase);
        //Borro el usuario de Firebase
        DeleteReference.removeValue();
        Toast.makeText(getApplicationContext(), "Clase eliminada", Toast.LENGTH_LONG).show();
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
        Intent i = new Intent(AdminClases.this, InicioPortalAdmin.class);
        startActivity(i);
    }

    private void ClickAgregarClases(View view){
        Intent i = new Intent(AdminClases.this, AgregarClase.class);
        startActivity(i);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    public void ClickSalir(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminClases.this);
        builder.setTitle("Salir");
        builder.setMessage("¿Deseas salir de UAO Remoto?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AdminClases.this.finishAffinity();
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