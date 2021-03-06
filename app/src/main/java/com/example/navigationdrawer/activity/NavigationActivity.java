package com.example.navigationdrawer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.navigationdrawer.Constants;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.fragment.HomeFragment;
import com.example.navigationdrawer.fragment.JobsFragment;
import com.example.navigationdrawer.fragment.MessagesFragment;
import com.example.navigationdrawer.fragment.WeatherFragment;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        pref = getSharedPreferences("Constants", Context.MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView tvFullName = (TextView) headerView.findViewById(R.id.tvFullName);
        tvFullName.setText(pref.getString(Constants.NAME, "Name Unavailable"));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
            setTitle("Home");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;

            case R.id.nav_weather:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WeatherFragment()).commit();
                break;

            case R.id.nav_jobs:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new JobsFragment()).commit();
                break;

            case R.id.nav_messages:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MessagesFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        setTitle(menuItem.getTitle());
        return true;
    }

    private void logout() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN,false);
        editor.putString(Constants.EMAIL,"");
        editor.putString(Constants.NAME,"");
        editor.putString(Constants.UNIQUE_ID,"");
        editor.apply();
        goToLogin();
    }

    private void goToLogin() {
        Intent loginIntent = new Intent(NavigationActivity.this, MainActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            // Do nothing
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        getMenuInflater().inflate(R.menu.menu, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = menuItem.getItemId();

        if(id == R.id.action_logout) {
            logout();
        }
        else if(id == R.id.action_settings) {
            goToSettings();
        }

        return super.onOptionsItemSelected(menuItem);
    }

    private void goToSettings() {
        Intent intent = new Intent(NavigationActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}
