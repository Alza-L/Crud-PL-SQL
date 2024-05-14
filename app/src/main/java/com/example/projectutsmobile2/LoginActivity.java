package com.example.projectutsmobile2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private String usernameDb, passwordDb;
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private LinearLayout linearLayout;
    private TextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findId(); // Deklarasi Id Login Activity

        dbHelper = new DatabaseHelper(this);
        int countRow = dbHelper.checkItemRow();

        // Cek Data User Account & Cek Data Item Row
        if (selectUsnPas() != 0) {
            linearLayout.setVisibility(View.GONE);
        }
        if (countRow == 0) {
            insertData();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() && !password.isEmpty()) {
                    etUsername.requestFocus();
                    Toast.makeText(LoginActivity.this, "Lengkapi username", Toast.LENGTH_SHORT).show();
                } else if (!username.isEmpty() && password.isEmpty()) {
                    etPassword.requestFocus();
                    Toast.makeText(LoginActivity.this, "Lengkapi password", Toast.LENGTH_SHORT).show();
                } else if (username.isEmpty() && password.isEmpty()) {
                    etUsername.requestFocus();
                    Toast.makeText(LoginActivity.this, "Lengkapi input", Toast.LENGTH_SHORT).show();
                } else if (!username.isEmpty() && !password.isEmpty()) {
                    if (usernameDb == null && passwordDb == null) {
                        Toast.makeText(LoginActivity.this, "Pastikan bahwa akun anda sudah terdaftar", Toast.LENGTH_SHORT).show();
                    } else if (username.equals(usernameDb) && password.equals(passwordDb)) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (!username.equals(usernameDb) || !password.equals(passwordDb)) {
                        Toast.makeText(LoginActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }); // Method Button Login

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        }); // Method ke Signup Activity
    }

    private void findId() {
        etUsername = (EditText) findViewById(R.id.editTextUsername);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
        linearLayout = (LinearLayout) findViewById(R.id.toSignup);
        tvSignup = (TextView) findViewById(R.id.textViewSignup);
    } // Method Deklarasi Id Login Activity

    private int selectUsnPas() {
        Cursor cursor = dbHelper.takeUserAccount();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                this.usernameDb = cursor.getString(0);
                this.passwordDb = cursor.getString(1);
            }
        }
        return cursor.getCount();
    } // Method Select User Account

    private void insertData() {
        dbHelper.insertTableCategory(R.drawable.sword,"Sword");
        dbHelper.insertTableCategory(1,"Arrow");
        dbHelper.insertTableCategory(1,"Axe");
        dbHelper.insertTableCategory(1,"Katana");
        dbHelper.insertTableCategory(1,"Shield");
        dbHelper.insertTableCategory(1,"Mask");
        dbHelper.insertTableCategory(1,"Potion");
        dbHelper.insertTableItem("Basic Sword", "34,276.02", "5", 1);
    } // Method Insert Data
}