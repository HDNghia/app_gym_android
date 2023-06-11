package com.example.thanh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.thanh.R;
import com.google.android.material.navigation.NavigationView;

public abstract class NavActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(getCheckedNavigationItemId());
    }

    protected abstract int getLayoutResourceId();

    protected abstract int getCheckedNavigationItemId();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == getCheckedNavigationItemId()) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        switch (itemId) {
            case R.id.nav_courses:
                Intent coursesIntent = new Intent(this, course_user_gets.class);
                startActivity(coursesIntent);
                break;
            case R.id.nav_yourcourses:
                Intent yourCoursesIntent = new Intent(this, course_user_signed_gets.class);
                startActivity(yourCoursesIntent);
                break;
            case R.id.nav_mess:
                Intent conversationIntent = new Intent(this, conversation_user_gets.class);
                startActivity(conversationIntent);
                break;
            case R.id.nav_food:
                Intent foodIntent = new Intent(this, food_user_gets.class);
                startActivity(foodIntent);
                break;
            case R.id.nav_home:
                Intent homeIntent = new Intent(this, post_user_gets.class);
                startActivity(homeIntent);
                break;
            default:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}