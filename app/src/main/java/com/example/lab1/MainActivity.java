package com.example.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button btnDkSDT , btnDkEmail , btnDangNhap ;
    EditText edtUser , edtPass ;
    
    FirebaseAuth auth = FirebaseAuth.getInstance() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          anhXa();

          Bundle bundle = getIntent().getExtras();

          if (bundle == null){
              edtUser.setText("");
              edtPass.setText("");
          }else {
                String ten = bundle.getString("user");
                String mk = bundle.getString("pass");
              edtUser.setText(ten);
              edtPass.setText(mk);
          }

            //Đăng nập
        
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auth.signInWithEmailAndPassword(edtUser.getText().toString(),edtPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,TrangChu.class));
                        }else {
                            Toast.makeText(MainActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

          //Đổi hướng sang đang kí
        btnDkEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DangkiEmail.class));
            }
        });

          btnDkSDT .setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startActivity(new Intent(MainActivity.this, DangKiSDT.class));

              }
          });


    }

    public  void  anhXa () {
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDkEmail = findViewById(R.id.btnDangKiEmail);
        btnDkSDT = findViewById(R.id.btnDangKiSDT);

        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
    }
}