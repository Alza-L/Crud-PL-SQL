package com.example.projectutsmobile2.dashboard;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectutsmobile2.main.DatabaseHelper;
import com.example.projectutsmobile2.main.ItemAdapter;
import com.example.projectutsmobile2.main.ItemDataModel;
import com.example.projectutsmobile2.R;
import com.example.projectutsmobile2.dashboard.action.AddActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    DatabaseHelper dbHelper;
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    SearchView searchView;
    TextView dashboardTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        findId(view);

        getallData();

        searchView.clearFocus();
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dashboardTitle.setVisibility(view.INVISIBLE);
                    searchView.setMaxWidth(Integer.MAX_VALUE);
                } else {
                    dashboardTitle.setVisibility(view.VISIBLE);
                    searchView.setMaxWidth(Integer.MAX_VALUE);
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearchItem(newText);
                return true;
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void onResume() {
        super.onResume();
        getallData();
    }

    private void getallData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<ItemDataModel> list = new ArrayList<>();
        ItemAdapter adapter = new ItemAdapter(list);
        Cursor cursor = dbHelper.getAllItems();
        if (cursor != null) {
            int idIndex = cursor.getColumnIndex("item_id");
            int nameIndex = cursor.getColumnIndex("item_name");
            int priceIndex = cursor.getColumnIndex("item_price");
            int stockIndex = cursor.getColumnIndex("item_stock");
            int categoryId = cursor.getColumnIndex("category_id");
            int imageIdIndex = cursor.getColumnIndex("ITEM_COLUMN_CATEGORY_IMAGEID");
            int categoryNameIndex = cursor.getColumnIndex("ITEM_COLUMN_CATEGORY_NAME");

            while (cursor.moveToNext()) {
                int id_item = cursor.getInt(idIndex);
                String name_item = cursor.getString(nameIndex);
                String price_item = cursor.getString(priceIndex);
                String stock_item = cursor.getString(stockIndex);
                int category_id = cursor.getInt(categoryId);
                int image_id = cursor.getInt(imageIdIndex);
                String category_name = cursor.getString(categoryNameIndex);

                list.add(new ItemDataModel(id_item, name_item, price_item, stock_item, category_id, image_id, category_name));
            }
            cursor.close();
        }
        recyclerView.setAdapter(adapter);
    }

    private void getSearchItem(String search) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<ItemDataModel> list = new ArrayList<>();
        ItemAdapter adapter = new ItemAdapter(list);
        Cursor cursor = dbHelper.searchItem(search);
        if (cursor != null) {
            int idIndex = cursor.getColumnIndex("item_id");
            int nameIndex = cursor.getColumnIndex("item_name");
            int priceIndex = cursor.getColumnIndex("item_price");
            int stockIndex = cursor.getColumnIndex("item_stock");
            int categoryId = cursor.getColumnIndex("category_id");
            int imageIdIndex = cursor.getColumnIndex("ITEM_COLUMN_CATEGORY_IMAGEID");
            int categoryNameIndex = cursor.getColumnIndex("ITEM_COLUMN_CATEGORY_NAME");

            while (cursor.moveToNext()) {
                int id_item = cursor.getInt(idIndex);
                String name_item = cursor.getString(nameIndex);
                String price_item = cursor.getString(priceIndex);
                String stock_item = cursor.getString(stockIndex);
                int category_id = cursor.getInt(categoryId);
                int image_id = cursor.getInt(imageIdIndex);
                String category_name = cursor.getString(categoryNameIndex);

                list.add(new ItemDataModel(id_item, name_item, price_item, stock_item, category_id, image_id, category_name));
            }
            cursor.close();
        }
        recyclerView.setAdapter(adapter);
    }

    private void findId(View view) {
        dbHelper = new DatabaseHelper(getActivity());
        recyclerView = view.findViewById(R.id.recyclerView);
        add_button = view.findViewById(R.id.add_button);
        searchView = view.findViewById(R.id.searchView);
        dashboardTitle = view.findViewById(R.id.titleDashboard);
    }
}