package com.example.project_work;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "123";
    private static final String PREF_NAME = "user_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameEdit = findViewById(R.id.edit_username);
        EditText passwordEdit = findViewById(R.id.edit_password);
        Button loginButton = findViewById(R.id.btn_login);

        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

//        prefs.edit().clear().apply();
//        Log.d("LOGIN", "SharedPreferences cleared");

// При первом запуске — создаём пользователя admin:123
        if (!prefs.contains(DEFAULT_USERNAME)) {
            prefs.edit().putString(DEFAULT_USERNAME, DEFAULT_PASSWORD).apply();
            Log.d("LOGIN", "Default user created: admin/123");
        }

        usernameEdit.setHint("Username (default: admin)");
        passwordEdit.setHint("Password (default: 123)");

        loginButton.setOnClickListener(v -> {
            String username = usernameEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username and password can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            String savedPassword = prefs.getString(username, null);

            Log.d("LOGIN", "Input: " + username + " / " + password);
            Log.d("LOGIN", "Saved: " + savedPassword);

            if (savedPassword == null) {
                // Новый пользователь — регистрируем
                prefs.edit().putString(username, password).apply();
                Toast.makeText(this, "New user registered", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            } else if (savedPassword.equals(password)) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
