package com.example.lab6_20175947_20180252;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText editTextEmail,editTextPassword;
    Button btn_register, btn_to_login;
    FirebaseAuth mAuth;
//para realtime :
    FirebaseDatabase firebaseDatabase;
//PARA CLOUD:
    FirebaseFirestore mFirestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ///realtime:
        firebaseDatabase =FirebaseDatabase.getInstance();
        /////
        mAuth = FirebaseAuth.getInstance();
        editTextEmail=findViewById(R.id.email_r);
        editTextPassword=findViewById(R.id.password_r);

        btn_to_login=findViewById(R.id.btn_to_login);
        btn_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();

            }
        });

        btn_register=findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_reg,password_reg;
                email_reg=String.valueOf(editTextEmail.getText());
                password_reg=String.valueOf(editTextPassword.getText());


                if ( email_reg.isEmpty() && password_reg.isEmpty()){
                    Toast.makeText(Register.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                }else{
                    registerUser( email_reg, password_reg);
                }


                /*
//realtime:


                DatabaseReference databaseReference=firebaseDatabase.getReference();
                Usuario usuario = new Usuario();
                usuario.setCorreo(email_reg);
                usuario.setContraseña(password_reg);

                // Crea y agrega actividades a la lista de actividades del usuario
                List<ActividadItem> actividadesList = new ArrayList<>();
                ActividadItem actividad1 = new ActividadItem("1", "Título 1", "2023-06-27", "10:00", "11:00");
                ActividadItem actividad2 = new ActividadItem("2", "Título 2", "2023-06-28", "14:00", "15:30");
                actividadesList.add(actividad1);
                actividadesList.add(actividad2);

// Asigna la lista de actividades al usuario
                usuario.setActividades(actividadesList);

                */

//fin realtime
//inicio cloud


//cloud










            }
        });
    }
    private void registerUser( String emailUser, String passUser) {
        mAuth.createUserWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id = mAuth.getCurrentUser().getUid();
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("email", emailUser);
                map.put("password", passUser);

                FirebaseFirestore.getInstance().collection("users").
                        document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Register.this, "Registrado en cloud", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Register.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}