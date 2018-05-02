package com.example.lahiru.moments;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText reg_email_field;
    private EditText reg_pass_field;
    private EditText reg_confirm_pass_field;
    private Button reg_btn;
    private Button reg_login_btn;
    private ProgressBar reg_progress;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        reg_email_field = findViewById(R.id.reg_email);
        reg_pass_field = findViewById(R.id.reg_password);
        reg_confirm_pass_field = findViewById(R.id.reg_confim_pass);
        reg_btn = findViewById(R.id.reg_create_acc);
        reg_login_btn = findViewById(R.id.reg_register_btn);
        reg_progress = findViewById(R.id.reg_progress);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = reg_email_field.getText().toString();
                String password = reg_pass_field.getText().toString();
                String confirmPassword = reg_confirm_pass_field.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {

                    if (password.equals(confirmPassword)) {

                        reg_progress.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    sendToMain();

                                } else {

                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, "Error : " + errorMessage, Toast.LENGTH_SHORT).show();

                                }

                                reg_progress.setVisibility(View.INVISIBLE);

                            }
                        });

                    } else {

                        Toast.makeText(RegisterActivity.this, "Confirm password does not match with password", Toast.LENGTH_LONG).show();

                    }

                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            sendToMain();

        }
    }

    private void sendToMain() {

        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }


}
