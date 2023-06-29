package com.example.lab6_20175947_20180252;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {




    RecyclerView recyclerView2;
    ArrayList<ActividadItem> actividadItemArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;


    ///////////////////////
    Button buttonAdd;
    FirebaseAuth auth;
    Button logout;
    FirebaseUser user;
    TextView userdetails;

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////////////////////////////////////////////////////////////////////////////////
        //INICIAR SESION :
        auth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.btn_logout);
        user = auth.getCurrentUser();
        userdetails = findViewById(R.id.userdetails);
        if(user == null){
            Intent intent = new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            finish();
        }
        else {
            userdetails.setText(user.getEmail());

        }
        //CERRAR SESION:
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
//////


//funciona:
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogAdd viewDialogAdd = new ViewDialogAdd();
                viewDialogAdd.showDialog(MainActivity.this);

            }
        });

        //readData();
        //nuevo:


        recyclerView2=(RecyclerView) findViewById(R.id.recyclerView);

        recyclerView2.setLayoutManager(new LinearLayoutManager(this));


        actividadItemArrayList=new ArrayList<>();

        myAdapter = new MyAdapter(MainActivity.this,actividadItemArrayList);
        recyclerView2.setAdapter(myAdapter);


        String id = auth.getCurrentUser().getUid();

        EventChangeListener(id);




    }

    private void EventChangeListener(String id) {

        FirebaseFirestore.getInstance().collection("users").document(id).collection("actividades").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){

                            ActividadItem obj=d.toObject(ActividadItem.class);
                            actividadItemArrayList.add(obj);
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                });



                //.orderBy("fecha", Query.Direction.ASCENDING)
                /*
        .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.e("Firestore error",error.getMessage());
                    return;

                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType()==DocumentChange.Type.ADDED){
                        actividadItemArrayList.add(dc.getDocument().toObject(ActividadItem.class));
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }
        });*/
    }



    //AGREGAR ACTIVIDAD (VISTA)
    public class ViewDialogAdd {
        public void showDialog(Context context) {
            String id_auth =auth.getCurrentUser().getUid();

            /////////////VIDEO:
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.add_new_activity);
//BIEN
            EditText textNameadd = dialog.findViewById(R.id.textNameAdd);
            EditText textFechaadd = dialog.findViewById(R.id.textFechaAdd);
            EditText textInicioadd = dialog.findViewById(R.id.textInicioAdd);
            EditText textFinadd = dialog.findViewById(R.id.textFinAdd);


            Button buttonAdd = dialog.findViewById(R.id.buttonAdd);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
            //

            buttonAdd.setText("Agregar");//BIEN
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id_new = "activity" + new Date().getTime();
                    String name = textNameadd.getText().toString();
                    String fechaTexto = textFechaadd.getText().toString();
                    //LocalDate fecha = LocalDate.parse(fechaTexto);
                    String inicioTexto = textInicioadd.getText().toString();
                    //LocalTime inicio = LocalTime.parse(inicioTexto);
                    String finTexto = textFinadd.getText().toString();
                    //LocalTime ffinal = LocalTime.parse(finTexto);

                    //
                    String id = auth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", id_new);
                    map.put("titulo", name);
                    map.put("fecha", fechaTexto);
                    map.put("hora_inicio", inicioTexto);
                    map.put("hora_fin", finTexto);

                    if (name.isEmpty() || fechaTexto.isEmpty() || inicioTexto.isEmpty() || finTexto.isEmpty()) {
                        Toast.makeText(context, "LLene todos los datos", Toast.LENGTH_SHORT).show();
                    } else {
                            FirebaseFirestore.getInstance().collection("users").
                                document(id).collection("actividades").document(id_new).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MainActivity.this, "Actividad registrada", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, "no registrado", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        //databaseReference.child(id_auth).child("actividades").setValue(new ActividadItem(id_new, name, fechaTexto, inicioTexto,finTexto));
                        Toast.makeText(context, "Hecho!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();


                    }
                }
            });


            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }




}