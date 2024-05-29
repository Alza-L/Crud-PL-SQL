package com.example.projectutsmobile2.dashboard.action;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectutsmobile2.main.DatabaseHelper;
import com.example.projectutsmobile2.main.MainActivity;
import com.example.projectutsmobile2.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class ItemEditActivity extends AppCompatActivity {
    private Object[] data;
    private DatabaseHelper dbHelper;
    private CardView btnBack;
    private ChipGroup chipCategory;
    private EditText etName, etPrice, etStock;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        data = (Object[]) intent.getSerializableExtra("dataItem");
//        Log.d("dataItem", data[0].toString());
//        Log.d("dataItem", data[1].toString());
//        Log.d("dataItem", data[2].toString());
//        Log.d("dataItem", data[3].toString());
        Log.d("ItemEditActivity", data[4].toString());
//        Log.d("dataItem", data[5].toString());
//        Log.d("dataItem", data[6].toString());

        findId();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Chip chip = new Chip(this);
        chip.setId((int) data[4]);
        chip.setText((CharSequence) data[6]);
        chip.setChipBackgroundColor(getResources().getColorStateList(R.color.black));
        chip.setTextColor(getResources().getColorStateList(R.color.white));
        chip.setChipStrokeWidth(3);
        chip.setChipMinHeight(130);
        chipCategory.addView(chip);

        etName.setText((CharSequence) data[1]);
        etPrice.setText((CharSequence) data[2]);
        etStock.setText((CharSequence) data[3]);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = (int) data[0];
                String name = etName.getText().toString();
                String price = etPrice.getText().toString();
                String stock = etStock.getText().toString();
                int categoryId = (int) data[4];
                if (!name.isEmpty() || !price.isEmpty() || !stock.isEmpty()) {
                    boolean result = dbHelper.updateItem(id, name, price, stock, categoryId);
                    if (result) {
                        Toast.makeText(ItemEditActivity.this, "Item berhasil diubah!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ItemEditActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(ItemEditActivity.this, "Lengkapi input!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void findId() {
        btnBack = findViewById(R.id.cardViewBack);
        dbHelper = new DatabaseHelper(this);
        chipCategory = findViewById(R.id.chipCategory);
        etName = findViewById(R.id.editTextItemName);
        etPrice = findViewById(R.id.editTextItemPrice);
        etStock = findViewById(R.id.editTextItemStock);
        btnSave = findViewById(R.id.buttonSaveItem);
    }
}