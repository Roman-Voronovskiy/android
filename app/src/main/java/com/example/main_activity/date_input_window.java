package com.example.main_activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.main_activity.moodle.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class date_input_window extends AppCompatActivity {
    Button btnResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_input_window);
        btnResult=findViewById(R.id.ButtonResult);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showResultWindow();
            }
        });
    }
   /* private void showResultWindow() {/// ПОИСПРАВЛЯТЬ ПОД ОКНО РЕЗУЛЬТАТА!!!!!!!!!!!
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
    }*/

}