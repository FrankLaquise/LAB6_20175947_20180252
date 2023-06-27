package com.example.lab6_20175947_20180252;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<ActividadItem> usersItemArrayList;
    ActividadesRecyclerAdapter adapter;
    Button buttonAdd;


    ///////////////////////
    FirebaseAuth auth;
    Button logout;
    FirebaseUser user;
    TextView userdetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true); // work offline
        Objects.requireNonNull(getSupportActionBar()).hide();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersItemArrayList = new ArrayList<>();


        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogAdd viewDialogAdd = new ViewDialogAdd();
                viewDialogAdd.showDialog(MainActivity.this);

            }
        });
        readData();








        //////////////////////////////////////////////////////////////////////////////////////////

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
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void readData() {
        String id =auth.getCurrentUser().getUid();

        databaseReference.child(id).orderByChild("fecha").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersItemArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ActividadItem users = dataSnapshot.getValue(ActividadItem.class);
                    usersItemArrayList.add(users);
                }
                adapter = new ActividadesRecyclerAdapter(MainActivity.this, usersItemArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public class ViewDialogAdd {
        public void showDialog(Context context) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.add_new_activity);

            EditText textNameadd = dialog.findViewById(R.id.textNameAdd);
            EditText textFechaadd = dialog.findViewById(R.id.textFechaAdd);
            EditText textInicioadd = dialog.findViewById(R.id.textInicioAdd);
            EditText textFinadd = dialog.findViewById(R.id.textFinAdd);


            Button buttonAdd = dialog.findViewById(R.id.buttonAdd);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonAdd.setText("Agregar");
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = "activity" + new Date().getTime();
                    String name = textNameadd.getText().toString();



                    String fechaTexto = textFechaadd.getText().toString();
                    //LocalDate fecha = LocalDate.parse(fechaTexto);

                    String inicioTexto = textInicioadd.getText().toString();
                    //LocalTime inicio = LocalTime.parse(inicioTexto);

                    String finTexto = textFinadd.getText().toString();
                    //LocalTime ffinal = LocalTime.parse(finTexto);

                    if (name.isEmpty() || fechaTexto.isEmpty() || inicioTexto.isEmpty() || finTexto.isEmpty()) {
                        Toast.makeText(context, "LLene todos los datos", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseReference.child(id).child("actividades").setValue(new ActividadItem(id, name, fechaTexto, inicioTexto,finTexto));
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