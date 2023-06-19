package com.example.thanh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.thanh.R;
import com.example.thanh.model.User;
import com.google.android.material.navigation.NavigationView;

import java.security.acl.Group;
import java.util.List;

public abstract class NavActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected Toolbar toolbar;
    private DatabaseHelper databaseHelper;

    private MenuView.ItemView Courses;
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        drawerLayout = findViewById(R.id.drawerLayout);
        databaseHelper = new DatabaseHelper(this);
        navigationView = findViewById(R.id.nav_view);
        Courses = findViewById(R.id.nav_courses);
        toolbar = findViewById(R.id.toolbar);
        List<User> ul = databaseHelper.getAllUser();
        u = ul.get(0);
        Menu menu = navigationView.getMenu();
        MenuItem teacherMenuItem = menu.findItem(R.id.nav_yourcourses);
        MenuItem CourseView = menu.findItem(R.id.nav_courses);
        MenuItem trainerView = menu.findItem(R.id.nav_trainercourse);
        if (ul.get(0).getRole() == 2) {
            teacherMenuItem.setVisible(false);
            CourseView.setVisible(false);
            trainerView.setVisible(true);
        } else {
            teacherMenuItem.setVisible(true);
            CourseView.setVisible(true);
            trainerView.setVisible(false);
        }

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
            case R.id.nav_profile:
                Intent profileIntent = new Intent(this, profile_user_get.class);
                startActivity(profileIntent);
                break;
            case R.id.nav_trainercourse:
                Intent trainerIntent = new Intent(this, course_trainer_get.class);
                startActivity(trainerIntent);
//                Toast.makeText(this, "You don't have permission!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                databaseHelper.deleteAll();
                Intent login = new Intent(this, login.class);
                startActivity(login);
                finish();
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