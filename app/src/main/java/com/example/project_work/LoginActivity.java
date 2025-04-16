package com.example.project_work;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "password123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameEdit = findViewById(R.id.edit_username);
        EditText passwordEdit = findViewById(R.id.edit_password);
        Button loginButton = findViewById(R.id.btn_login);

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);


        if (!prefs.contains(DEFAULT_USERNAME)) {

            prefs.edit().putString(DEFAULT_USERNAME, DEFAULT_PASSWORD).apply();
        }

        usernameEdit.setHint("Username (default: " + DEFAULT_USERNAME + ")");
        passwordEdit.setHint("Password (default: " + DEFAULT_PASSWORD + ")");

        loginButton.setOnClickListener(v -> {
            String username = usernameEdit.getText().toString();
            String password = passwordEdit.getText().toString();

            String savedPassword = prefs.getString(username, null);
            if (savedPassword == null) {
                // Register new user
                prefs.edit().putString(username, password).apply();
                startActivity(new Intent(this, MainActivity.class));
            } else if (savedPassword.equals(password)) {
                // Login successful
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}