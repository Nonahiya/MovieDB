package com.enricowakiman.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.enricowakiman.moviedb.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    NavHostFragment nhf;
    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        tb = findViewById(R.id.tb_mainmenu);
        setSupportActionBar(tb);
        bnv = findViewById(R.id.bottom_nav_mainmenu);
        nhf = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_mainmenu);

        AppBarConfiguration abc = new AppBarConfiguration
                .Builder(R.id.nowPlayingFragment, R.id.upComingFragment)
                .build();
        NavigationUI.setupActionBarWithNavController(MainMenuActivity.this, nhf.getNavController(), abc);
        NavigationUI.setupWithNavController(bnv, nhf.getNavController());
    }
}