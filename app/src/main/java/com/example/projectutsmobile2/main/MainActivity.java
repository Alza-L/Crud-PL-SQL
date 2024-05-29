package com.example.projectutsmobile2.main;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.projectutsmobile2.R;
import com.example.projectutsmobile2.dashboard.DashboardFragment;
import com.example.projectutsmobile2.profile.ProfileFragment;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    protected final int dashboard = 1;
    protected final int profile = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MeowBottomNavigation meowBottomNavigation = findViewById(R.id.bottomNavigation);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(dashboard, R.drawable.ic_dashboard_black_24dp));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(profile, R.drawable.ic_account_circle_24));
        meowBottomNavigation.show(dashboard, true);

        meowBottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case dashboard:
                        replaceFragment(new DashboardFragment());
                        break;
                    case profile:
                        replaceFragment(new ProfileFragment());
                        break;
                }
                return null;
            }
        }); // MeowButton OnShowListener
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    } // Method ReplaceFragment
}