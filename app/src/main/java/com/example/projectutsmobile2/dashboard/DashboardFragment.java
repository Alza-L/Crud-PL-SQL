package com.example.projectutsmobile2.dashboard;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectutsmobile2.DatabaseHelper;
import com.example.projectutsmobile2.ItemAdapter;
import com.example.projectutsmobile2.ItemDataModel;
import com.example.projectutsmobile2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    DatabaseHelper dbHelper;
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        findId(view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<ItemDataModel> list = new ArrayList<>();
        ItemAdapter adapter = new ItemAdapter(list);
        Cursor cursor = dbHelper.getAllItems();
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex("item_name");
            int priceIndex = cursor.getColumnIndex("item_price");
            int stockIndex = cursor.getColumnIndex("item_stock");
            int imageIdIndex = cursor.getColumnIndex("ITEM_COLUMN_CATEGORY_IMAGEID");
            int categoryNameIndex = cursor.getColumnIndex("ITEM_COLUMN_CATEGORY_NAME");

            while (cursor.moveToNext()) {
                String name_item = cursor.getString(nameIndex);
                String price_item = cursor.getString(priceIndex);
                String stock_item = cursor.getString(stockIndex);
                int image_id = cursor.getInt(imageIdIndex);
                String category_name = cursor.getString(categoryNameIndex);
                Log.d("Data", "Name: " + name_item + ", Price: " + price_item + ", Stock: " + stock_item);

                list.add(new ItemDataModel(name_item, price_item, stock_item, image_id, category_name));
            }
            cursor.close(); // Menutup cursor setelah selesai digunakan
        }
        recyclerView.setAdapter(adapter);


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        }); // Button Add
        return view;
    }

    private void findId(View view) {
        dbHelper = new DatabaseHelper(getActivity());
        recyclerView = view.findViewById(R.id.recyclerView);
        add_button = view.findViewById(R.id.add_button);
    }
}