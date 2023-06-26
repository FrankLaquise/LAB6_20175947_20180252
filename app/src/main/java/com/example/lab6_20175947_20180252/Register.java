package com.example.lab6_20175947_20180252;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    EditText editTextEmail,editTextPassword;
    Button btn_register, btn_to_login;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

                if (TextUtils.isEmpty(email_reg)){
                    Toast.makeText(Register.this,"Ingrese correo",Toast.LENGTH_LONG);
                    return;
                }
                if (TextUtils.isEmpty(password_reg)){
                    Toast.makeText(Register.this,"Ingrese contraseña",Toast.LENGTH_LONG);
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email_reg, password_reg)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    Toast.makeText(Register.this, "Cuenta creada",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(Register.this, "Fallo al registrar",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}