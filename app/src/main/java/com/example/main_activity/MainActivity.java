package com.example.main_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.main_activity.moodle.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {
        Button btnSignIn,btnRegister;
        FirebaseAuth auth;
        FirebaseDatabase db;
        DatabaseReference users;
        RelativeLayout root;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignIn=findViewById(R.id.ButtonSignIn);
        btnRegister=findViewById(R.id.ButtonRegister);
        auth=FirebaseAuth.getInstance();
        root=findViewById(R.id.root_element);
        db=FirebaseDatabase.getInstance();
        users=db.getReference("Users");
        btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        showRegisterWindow();
                }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        showSignInWindow();
                }
        });
        }
        private void showSignInWindow(){
                AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                dialog.setTitle("Войти");
                dialog.setMessage("Введите данные для входа");
                LayoutInflater inflater=LayoutInflater.from(this);
                View sign_in_window=inflater.inflate(R.layout.sign_in_window,null);
                dialog.setView(sign_in_window);
                final MaterialEditText email=sign_in_window.findViewById(R.id.email_field);
                final MaterialEditText password=sign_in_window.findViewById(R.id.pass_field);

                dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                        }
                });
                dialog.setPositiveButton("Войти", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                                //проверка вводимых полей
                                if(TextUtils.isEmpty(email.getText().toString())) {
                                        Snackbar.make(root,"Введите вашу почту",Snackbar.LENGTH_LONG).show();
                                        return;
                                }

                                if(password.getText().toString().length()<5) {
                                        Snackbar.make(root,"Введите пароль, который больше 5 символов",Snackbar.LENGTH_LONG).show();
                                        return;
                                }
                auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {//успешная авторизация
                                startActivity(new Intent(MainActivity.this,date_input_window.class));
                                finish();
                        }
                }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {//не успешная авторизация
                                Snackbar.make(root,"Ошибка авторизации"+e.getMessage(),Snackbar.LENGTH_SHORT).show();
                        }
                });

                        }
                });
                dialog.show();
        }
        private void showRegisterWindow() {
                AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                dialog.setTitle("Зарегистрироваться");
                dialog.setMessage("Введите все данные для регистрации");
                LayoutInflater inflater=LayoutInflater.from(this);
                View register_window=inflater.inflate(R.layout.register_window,null);
                dialog.setView(register_window);
                final MaterialEditText email=register_window.findViewById(R.id.email_field);
                final MaterialEditText password=register_window.findViewById(R.id.pass_field);
                final MaterialEditText name=register_window.findViewById(R.id.name_field);
                final MaterialEditText phone=register_window.findViewById(R.id.phone_field);
                dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                               dialogInterface.dismiss();
                        }
                });
                dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                                //проверка вводимых полей
                                if(TextUtils.isEmpty(email.getText().toString())) {
                                        Snackbar.make(root,"Введите вашу почту",Snackbar.LENGTH_LONG).show();
                                        return;
                                }
                                if(TextUtils.isEmpty(name.getText().toString())) {
                                        Snackbar.make(root,"Введите ваше имя",Snackbar.LENGTH_LONG).show();
                                        return;
                                }
                                if(password.getText().toString().length()<5) {
                                        Snackbar.make(root,"Введите пароль, который больше 5 символов",Snackbar.LENGTH_LONG).show();
                                        return;
                                }
                                if(TextUtils.isEmpty(phone.getText().toString())) {
                                        Snackbar.make(root,"Введите ваш телефон",Snackbar.LENGTH_LONG).show();
                                        return;
                                }
                                //регистрация пользователя в бд
                                auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                @Override
                                                public void onSuccess(AuthResult authResult) {
                                                        User user=new User();
                                                        user.setEmail(email.getText().toString());
                                                        user.setName(name.getText().toString());
                                                        user.setPassword(password.getText().toString());
                                                        user.setPhone(phone.getText().toString());
                                                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                        Snackbar.make(root,"Пользователь добавлен",Snackbar.LENGTH_SHORT).show();
                                                                }
                                                        });
                                                }
                                        });
                        }
                });
                dialog.show();
        }
}