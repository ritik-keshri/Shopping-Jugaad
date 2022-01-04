package com.example.shoppingjugaad;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.shoppingjugaad.Fragment.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Create an adapter that knows which fragment should be shown on each page
        ViewPager viewPager = findViewById(R.id.view_pager);

        // Find the view pager that will allow the user to swipe between fragments
        FragmentAdapter fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(fragmentAdapter);

        //To find the id of the tab for showing it
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

}