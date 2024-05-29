package com.example.projectutsmobile2.profile;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectutsmobile2.main.DatabaseHelper;
import com.example.projectutsmobile2.R;

public class ManageProfileActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private String type, data, dataLama;
    private TextView title;
    private CardView cardBack;
    private EditText etUsername, etEmail, etLastPassword, etNewPassword;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        etUsername = findViewById(R.id.editTextUsername);
        etEmail = findViewById(R.id.editTextEmail);
        etLastPassword = findViewById(R.id.editTextPassword);
        etNewPassword = findViewById(R.id.editTextNewPassword);
        btnUpdate = findViewById(R.id.buttonUpdate);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        data = intent.getStringExtra("data");
        title = findViewById(R.id.titleManage);
        title.setText("Chance "+type);
        dataLama = data;

        cardBack = findViewById(R.id.cardViewBack);
        cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (type.equals("Username")) {
            etUsername.setVisibility(VISIBLE);
            etUsername.setText(data);
            below(etUsername, R.id.titleManage);

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etUsername.getText().toString().trim().isEmpty()) {
                        Toast.makeText(ManageProfileActivity.this, "Lengapi input!", Toast.LENGTH_SHORT).show();
                    } else if (etUsername.getText().toString().trim().contains(" ")) {
                        Toast.makeText(ManageProfileActivity.this, "Tidak boleh ada spasi", Toast.LENGTH_SHORT).show();
                    } else if (etUsername.getText().toString().trim().equals(dataLama)) {
                        Toast.makeText(ManageProfileActivity.this, "Username sama seperti sebelumnya", Toast.LENGTH_SHORT).show();
                    } else {
                        String query = "UPDATE User SET username = '"+etUsername.getText().toString().trim()+"' WHERE username = '"+dataLama+"'";
                        boolean result = dbHelper.updateProfile(query);
                        if (result) {
                            Toast.makeText(ManageProfileActivity.this, "Username berhasil diperbarui", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ManageProfileActivity.this, "Gagal memperbarui username", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        } else if (type.equals("Email")) {
            etEmail.setVisibility(VISIBLE);
            etEmail.setText(data);
            below(etEmail, R.id.titleManage);

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etEmail.getText().toString().trim().isEmpty()) {
                        Toast.makeText(ManageProfileActivity.this, "Lengapi Input", Toast.LENGTH_SHORT).show();
                    } else if (etEmail.getText().toString().trim().equals(dataLama)) {
                        Toast.makeText(ManageProfileActivity.this, "Email sama seperti sebelumnya", Toast.LENGTH_SHORT).show();
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches()) {
                        Toast.makeText(ManageProfileActivity.this, "Format email tidak valid", Toast.LENGTH_SHORT).show();
                    } else {
                        String query = "UPDATE User SET email = '"+etEmail.getText().toString().trim()+"' WHERE email = '"+dataLama+"'";
                        boolean result = dbHelper.updateProfile(query);
                        if (result) {
                            Toast.makeText(ManageProfileActivity.this, "Email berhasil diperbarui", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ManageProfileActivity.this, "Gagal memperbarui email", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        } else if (type.equals("Password")) {
            etLastPassword.setVisibility(VISIBLE);
            etNewPassword.setVisibility(VISIBLE);
            below(etLastPassword, R.id.titleManage);
            below(etNewPassword, R.id.editTextPassword);

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etLastPassword.getText().toString().trim().isEmpty() || etNewPassword.getText().toString().trim().isEmpty()) {
                        Toast.makeText(ManageProfileActivity.this, "Lengkapi Input", Toast.LENGTH_SHORT).show();
                    } else  if (etNewPassword.getText().toString().trim().length() < 8) {
                        Toast.makeText(ManageProfileActivity.this, "Minimal 8 karakter", Toast.LENGTH_SHORT).show();
                    } else if (etNewPassword.getText().toString().trim().equals(dataLama)) {
                        Toast.makeText(ManageProfileActivity.this, "Pasword tidak boleh sama", Toast.LENGTH_SHORT).show();
                    } else if (!etLastPassword.getText().toString().trim().equals(dataLama)) {
                        Toast.makeText(ManageProfileActivity.this, "Pasword lama harus sesuai", Toast.LENGTH_SHORT).show();
                    } else {
                        String query = "UPDATE User SET password = '"+etNewPassword.getText().toString().trim()+"' WHERE password = '"+dataLama+"'";
                        boolean result = dbHelper.updateProfile(query);
                        if (result) {
                            Toast.makeText(ManageProfileActivity.this, "Password berhasil diperbarui", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ManageProfileActivity.this, "Gagal memperbarui password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }
    }

    private void below(EditText et1, int id) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) et1.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, id);
        et1.setLayoutParams(params);
    }
}