package com.enricowakiman.moviedb.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.enricowakiman.moviedb.R;
import com.enricowakiman.moviedb.view.fragments.MovieDetailsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener{

    BottomNavigationView bnv;
    NavHostFragment nhf;
    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        tb = findViewById(R.id.tb_mainmenu);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bnv = findViewById(R.id.bottom_nav_mainmenu);
        nhf = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_mainmenu);

        AppBarConfiguration abc = new AppBarConfiguration
                .Builder(R.id.nowPlayingFragment, R.id.upComingFragment)
                .build();
        NavigationUI.setupActionBarWithNavController(MainMenuActivity.this, nhf.getNavController(), abc);
        NavigationUI.setupWithNavController(bnv, nhf.getNavController());
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStack();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackStackChanged() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_nav_mainmenu);
        if (fragment instanceof MovieDetailsFragment) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    public int getPage() {
        return 1;
    }
}