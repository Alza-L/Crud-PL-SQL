package com.example.projectutsmobile2.register;

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

import com.example.projectutsmobile2.main.DatabaseHelper;
import com.example.projectutsmobile2.main.MainActivity;
import com.example.projectutsmobile2.R;

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
                this.passwordDb = cursor.getString(2);
            }
        }
        return cursor.getCount();
    } // Method Select User Account

    private void insertData() {
        dbHelper.insertTableCategory(R.drawable.img_sword,"Sword");
        dbHelper.insertTableCategory(R.drawable.img_magic_staff,"Magic Staff");
        dbHelper.insertTableCategory(R.drawable.img_katana, "Katana");
        dbHelper.insertTableCategory(R.drawable.img_axe, "Axe");
        dbHelper.insertTableCategory(R.drawable.img_mace, "Mace");
        dbHelper.insertTableCategory(R.drawable.img_shield, "Shied");
        dbHelper.insertTableCategory(R.drawable.img_potion, "Potion");
        dbHelper.insertTableCategory(R.drawable.img_knife, "Knife");
        dbHelper.insertTableCategory(R.drawable.img_helmet, "Helmet");

        dbHelper.insertTableItem("Throwing Knife", "5.00", "40", 8); // Knife
        dbHelper.insertTableItem("Basic Katana", "25.00", "8", 3); // Katana
        dbHelper.insertTableItem("Mystic Magic Staff", "55.00", "3", 2); // Magic Staff
        dbHelper.insertTableItem("Iron Sword", "10.50", "15", 1); // Sword
        dbHelper.insertTableItem("Health Potion", "5.00", "30", 7); // Potion
        dbHelper.insertTableItem("Dragon Katana", "75.00", "2", 3); // Katana
        dbHelper.insertTableItem("Iron Mace", "15.00", "15", 5); // Mace
        dbHelper.insertTableItem("Golden Helmet", "35.00", "7", 9); // Helmet
        dbHelper.insertTableItem("Leather Helmet", "10.00", "15", 9); // Helmet
        dbHelper.insertTableItem("Flanged Mace", "30.00", "8", 5); // Mace
        dbHelper.insertTableItem("Iron Axe", "12.00", "20", 4); // Axe
        dbHelper.insertTableItem("Enchanted Magic Staff", "30.00", "7", 2); // Magic Staff
        dbHelper.insertTableItem("Mana Potion", "7.00", "25", 7); // Potion
        dbHelper.insertTableItem("Steel Sword", "20.75", "10", 1); // Sword
        dbHelper.insertTableItem("Basic Knife", "3.00", "50", 8); // Knife
        dbHelper.insertTableItem("War Axe", "40.00", "6", 4); // Axe
        dbHelper.insertTableItem("Stamina Potion", "6.00", "28", 7); // Potion
        dbHelper.insertTableItem("Iron Shield", "20.00", "10", 6); // Shield
        dbHelper.insertTableItem("Samurai Katana", "40.00", "5", 3); // Katana
        dbHelper.insertTableItem("Dragon Shield", "50.00", "4", 6); // Shield
        dbHelper.insertTableItem("Iron Helmet", "20.00", "12", 9); // Helmet
        dbHelper.insertTableItem("Wooden Shield", "10.00", "18", 6); // Shield
        dbHelper.insertTableItem("Battle Axe", "25.00", "10", 4); // Axe
        dbHelper.insertTableItem("Diamond Sword", "50.00", "5", 1); // Sword
        dbHelper.insertTableItem("Wooden Mace", "8.00", "25", 5); // Mace
        dbHelper.insertTableItem("Basic Magic Staff", "15.00", "12", 2); // Magic Staff
        dbHelper.insertTableItem("Dagger", "12.00", "20", 8); // Knife

    } // Method Insert Data
}