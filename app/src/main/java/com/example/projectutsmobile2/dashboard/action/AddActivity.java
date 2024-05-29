package com.example.projectutsmobile2.dashboard.action;

import android.database.Cursor;
import android.os.Bundle;
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
import com.example.projectutsmobile2.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class AddActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private CardView btnBack;
    private ChipGroup chipGroup;
    private EditText etName, etPrice, etStock;
    private Button btnAdd;
    private int categoryId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findId();

        Cursor cursor = dbHelper.getCategory();
        Chip[] allChips = new Chip[cursor.getCount()];
        if (cursor != null && cursor.getCount() > 0) {
            int categoryid = cursor.getColumnIndex("category_id");
            int categoryName = cursor.getColumnIndex("category_name");

            while (cursor.moveToNext()) {
                final int id = cursor.getInt(categoryid);
                String name = cursor.getString(categoryName);

                final Chip chip = new Chip(this);
                chip.setChipBackgroundColor(getResources().getColorStateList(R.color.layout_background));
                chip.setChipStrokeColor(getResources().getColorStateList(R.color.black));
                chip.setTextColor(getResources().getColorStateList(R.color.black));
                chip.setChipStrokeWidth(3);
                chip.setChipMinHeight(130);

                chip.setId(id);
                chip.setText(name);

                chip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (Chip c : allChips) {
                            c.setChipBackgroundColor(getResources().getColorStateList(R.color.layout_background));
                            c.setTextColor(getResources().getColorStateList(R.color.black));
                        }
                        chip.setChipBackgroundColor(getResources().getColorStateList(R.color.black));
                        chip.setTextColor(getResources().getColorStateList(R.color.white));

                        categoryId = 0;
                        categoryId = chip.getId();
                    }
                });
                allChips[cursor.getPosition()] = chip;
                chipGroup.addView(chip);
            }
            cursor.close();
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameItem = etName.getText().toString();
                String priceItem = etPrice.getText().toString();
                String stockItem = etStock.getText().toString();
                if (categoryId == 0) {
                    Toast.makeText(AddActivity.this, "Pilih kategori!", Toast.LENGTH_SHORT).show();
                } else if (nameItem.isEmpty() || priceItem.isEmpty() || stockItem.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Lengkapi input!", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.insertTableItem(nameItem, priceItem, stockItem, categoryId);
                    finish();
                }
            }
        });
    }

    private void findId() {
        btnBack = findViewById(R.id.cardViewBack);
        dbHelper = new DatabaseHelper(this);
        chipGroup = findViewById(R.id.chip_group);
        etName = findViewById(R.id.editTextItemName);
        etPrice = findViewById(R.id.editTextItemPrice);
        etStock = findViewById(R.id.editTextItemStock);
        btnAdd = findViewById(R.id.buttonAddItem);
    }
}