package com.example.fabset;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView MainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainMenu = findViewById(R.id.MainMenu);

        MainMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        loadFrag(new HomeFragment());
                        break;
                    case R.id.nav_category:
                        loadFrag(new CategoryFragment());
                        break;
                    case R.id.nav_cart:
                        loadFrag(new CartFragment());
                        break;
                    case R.id.nav_account:
                        loadFrag(new AccountFragment());
                        break;
                    default:
                        loadFrag(new HomeFragment());
                        break;
                }
                return true;
            }
        });
        MainMenu.setSelectedItemId(R.id.nav_home);
    }
    public void loadFrag(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
    @Override
    public void onBackPressed() {
        // Leave this method empty to disable the back button
        // This prevents the activity from navigating back
    }

}