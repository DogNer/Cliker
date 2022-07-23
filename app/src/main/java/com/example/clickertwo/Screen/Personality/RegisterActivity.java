package com.example.clickertwo.Screen.Personality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clickertwo.MainActivity;
import com.example.clickertwo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText username, email, password;

    private ProgressBar progressBar;
    TextView text_log;

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    ImageButton btnBack;
    AppCompatButton btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });

        username = findViewById(R.id.text_name);
        email = findViewById(R.id.text_email);
        password = findViewById(R.id.text_password);

        auth = FirebaseAuth.getInstance();
        btnReg = findViewById(R.id.btn_register);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(RegisterActivity.this);
                pd.setMessage("Please wait...");
                pd.show();

                String str_username = username.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();

                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_email)
                        || TextUtils.isEmpty(str_password)){
                    Toast.makeText(RegisterActivity.this, "Write your data", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                else if (str_password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password is short. Enter more 6 chars", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                else {
                    registerd(str_username, str_email, str_password);
                    //registerd(username, email, password, phone_number);
                }
            }
        });
    }

    private void registerd(String username, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                            HashMap<String, Object> hasMap = new HashMap<>();
                            hasMap.put("id", userid);
                            hasMap.put("username", username);
                            hasMap.put("password", password);
                            hasMap.put("email", email);

                            reference.setValue(hasMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        pd.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        else {
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this, "You can't register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}