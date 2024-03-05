package com.example.lab1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DangkiEmail extends AppCompatActivity {
    Button  btnDangKi ;
    EditText edtUser , edtPass ;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki_email);
        anhXa();


      btnDangKi.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              
                auth.createUserWithEmailAndPassword(edtUser.getText().toString(),edtPass.getText().toString()).addOnCompleteListener(DangkiEmail.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(DangkiEmail.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("user",edtUser.getText().toString());
                                        bundle.putString("pass",edtPass.getText().toString());
                                        Intent intent = new Intent(DangkiEmail.this,MainActivity.class);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(DangkiEmail.this, "Đang kí thất bại", Toast.LENGTH_SHORT).show();
                                    }
                            }
                        });
              
          }
      });






    }

    public  void  anhXa () {
        btnDangKi= findViewById(R.id.btnDangKiEm);
        edtUser = findViewById(R.id.edtEmailDK);
        edtPass = findViewById(R.id.edtPassDK);
    }
}