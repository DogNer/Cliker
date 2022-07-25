package com.example.clickertwo.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.clickertwo.Adapter.ViewPagerAdapter;
import com.example.clickertwo.MainActivity;
import com.example.clickertwo.R;
import com.example.clickertwo.Screen.FragmentScreen.MainFragment;
import com.example.clickertwo.Screen.FragmentScreen.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class FirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.viewPager, new MainFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFr = null;
            switch (item.getItemId()){
                case R.id.ic_home:
                    selectedFr = new MainFragment();
                    break;
                case R.id.ic_profile:
                    selectedFr = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.viewPager, selectedFr)
                    .commit();
            return true;
        }
    };
}