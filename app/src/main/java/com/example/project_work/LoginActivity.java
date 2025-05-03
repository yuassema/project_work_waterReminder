package com.example.project_work;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Получаем TextInputEditText напрямую по их ID
        TextInputEditText etUsername = findViewById(R.id.et_username);
        TextInputEditText etPassword = findViewById(R.id.et_password);

        // Получаем кнопки (используем MaterialButton для совместимости)
        com.google.android.material.button.MaterialButton btnLogin = findViewById(R.id.btn_login);
        com.google.android.material.button.MaterialButton btnSignup = findViewById(R.id.btn_signup);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.equals("admin") && password.equals("123")) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        btnSignup.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Signup feature to be implemented", Toast.LENGTH_SHORT).show();
        });
    }
}