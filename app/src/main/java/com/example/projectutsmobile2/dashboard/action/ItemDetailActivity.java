package com.example.projectutsmobile2.dashboard.action;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

public class ItemDetailActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    private Object[] data;
    private CardView btnBack, btnMenu;
    private TextView itemName, itemPrice, itemstock, itemCategory;
    private ImageView imageCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findId();
        Intent intent = getIntent();
        data = (Object[]) intent.getSerializableExtra("dataItem");

        imageCategory.setBackgroundResource((Integer) data[5]);
        itemName.setText(data[1].toString());
        itemPrice.setText("$"+data[2].toString());
        itemstock.setText(data[3].toString());
        itemCategory.setText(data[6].toString());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }); // Back Button

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        }); // Call Method Show Popup Menu
    }

    private void findId() {
        dbHelper = new DatabaseHelper(this);
        btnBack = findViewById(R.id.cardViewBack);
        btnMenu = findViewById(R.id.cardViewMenu);
        itemName = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.itemPrice);
        itemstock = findViewById(R.id.itemStock);
        itemCategory = findViewById(R.id.itemCategory);
        imageCategory = findViewById(R.id.imageCategoryBackground);
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(ItemDetailActivity.this, v);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_edit) {
                    Toast.makeText(ItemDetailActivity.this, "Edit clicked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ItemDetailActivity.this, ItemEditActivity.class);
                    intent.putExtra("dataItem", data);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.menu_delete) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetailActivity.this);
                    LayoutInflater inflater = LayoutInflater.from(ItemDetailActivity.this);
                    View dialogView = inflater.inflate(R.layout.custom_dialog, null);
                    builder.setView(dialogView);

                    AlertDialog dialog = builder.create();

                    dialogView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialogView.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int id = (int) data[0];
                            boolean isDeleted = dbHelper.deleteItem(id);

                            if (isDeleted) {
                                Toast.makeText(ItemDetailActivity.this, "Item berhasil dihapus", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ItemDetailActivity.this, "Item tidak ditemukan atau gagal dihapus", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                    return true;
                } else {
                    return false;
                }
            }
        });

        popupMenu.show();
    } // Method Show Popup Menu
}
