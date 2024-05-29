package com.example.projectutsmobile2.profile;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectutsmobile2.R;

import java.util.ArrayList;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_me);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recycler);

        ArrayList<Integer> array = new ArrayList<>();
        array.add(R.drawable.img_plane_sky);
        array.add(R.drawable.img_sky);
        array.add(R.drawable.img_sea);
        array.add(R.drawable.img_meow);
        array.add(R.drawable.img_me);

        ImageAdapter adapter = new ImageAdapter(this, array);
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ImageView imageView, int img) {
                startActivity(new Intent(AboutMeActivity.this, ImageViewActivity.class).putExtra("image", img),
                        ActivityOptions.makeSceneTransitionAnimation(AboutMeActivity.this, imageView, "image").toBundle());
            }
        });
        recyclerView.setAdapter(adapter);
    }
}