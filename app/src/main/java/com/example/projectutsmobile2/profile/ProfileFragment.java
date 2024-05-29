package com.example.projectutsmobile2.profile;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectutsmobile2.main.DatabaseHelper;
import com.example.projectutsmobile2.R;

public class ProfileFragment extends Fragment {
    private DatabaseHelper dbHelper;
    private Cursor cursor;
    private TextView profileUsername;
    private CardView btnEditUsername, btnEditEmail, btnEditPassword, btnAboutMe;
    String type, username, email, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        dbHelper = new DatabaseHelper(getContext());
        profileUsername = view.findViewById(R.id.profileUsername);
        btnEditUsername = view.findViewById(R.id.cardEditUsername);
        btnEditEmail = view.findViewById(R.id.cardEditEmail);
        btnEditPassword = view.findViewById(R.id.cardEditPassword);
        btnAboutMe = view.findViewById(R.id.cardAboutMe);

        cursor = dbHelper.takeUserAccount();
        if (cursor.moveToFirst()) {
            profileUsername.setText(cursor.getString(0));
            username = cursor.getString(0);
            email = cursor.getString(1);
            password = cursor.getString(2);
        }

        btnEditUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentActivity("Username", username);
            }
        });

        btnEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentActivity("Email", email);
            }
        });

        btnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentActivity("Password", password);
            }
        });

        btnAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AboutMeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void onResume() {
        super.onResume();
        cursor = dbHelper.takeUserAccount();
        if (cursor.moveToFirst()) {
            profileUsername.setText(cursor.getString(0));
            username = cursor.getString(0);
            email = cursor.getString(1);
            password = cursor.getString(2);
        }
    }

    private void intentActivity(String type, String data) {
        Intent intent = new Intent(getContext(), ManageProfileActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("data", data);
        startActivity(intent);
    }
}