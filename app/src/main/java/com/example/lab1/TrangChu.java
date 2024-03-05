package com.example.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TrangChu extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    TextView tvThongTin ;
    Button btnDangXuat,btnDoiPass ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        anhXa();
        if (user.getEmail() == null ){
            tvThongTin.setText("Tài khoản: "+user.getPhoneNumber());
        }else {
            tvThongTin.setText("Tài khoản: "+user.getEmail());
        }

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(TrangChu.this,MainActivity.class));
            }
        });

        btnDoiPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getEmail()!= null){
                    auth.sendPasswordResetEmail(user.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(TrangChu.this, "Đã gửi mã", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(TrangChu.this, "Gửi mã thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    
                }

            }
        });


    }

    void  anhXa (){
        tvThongTin = findViewById(R.id.tvThongTin);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnDoiPass =findViewById(R.id.btnDoipass);
    }
}