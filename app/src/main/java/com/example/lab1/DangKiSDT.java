package com.example.lab1;

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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class DangKiSDT extends AppCompatActivity {
    Button btnDangKi ,btnNhanMa;
    EditText edtUser , edtOTP ;
    String verificationCode ;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    FirebaseAuth mAuth =FirebaseAuth.getInstance();
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Toast.makeText(DangKiSDT.this, "Gửi mã thành công", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(DangKiSDT.this, "Gửi mã thất bại", Toast.LENGTH_SHORT).show();
            Log.e("Loi",""+e);
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationCode = s;
            resendingToken = forceResendingToken;
        }
    } ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki_sdt);
        anhXa();



       btnNhanMa.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               sendOtp(edtUser.getText().toString());
           }
       });

       btnDangKi.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,edtOTP.getText().toString() );
               signInWithPhoneAuthCredential(credential);
           }
       });
    }

    public  void  anhXa () {
        btnDangKi= findViewById(R.id.btnDangKiSdt);
        btnNhanMa = findViewById(R.id.btnNhanMa);
        edtUser = findViewById(R.id.edtUserSdtDK);
        edtOTP = findViewById(R.id.edtPassSdtDK);
    }

    void  sendOtp (String phoneNumber ){

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DangKiSDT.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DangKiSDT.this,TrangChu.class);
                            startActivity(intent);
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(DangKiSDT.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();


                        }
                    }
                });
    }
}