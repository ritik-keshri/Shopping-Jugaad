package com.example.shoppingjugaad.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingjugaad.R;

public class Information extends AppCompatActivity {

    private TextView inf, name;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);


//        // my_child_toolbar is defined in the layout file
//        Toolbar myChildToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        init();

        Intent i = getIntent();
        //For receiving the image from fragment to information class.
        Bundle bundle = getIntent().getExtras();
        img.setImageResource(bundle.getInt("image"));
        //For showing the name of the place/
        name.setText(i.getStringExtra("name"));
        //For showing the information of the places that was received from fragments.
        inf.setText(bundle.getInt("inf"));
    }

    public void init(){
        name = findViewById(R.id.itemName);
        img = findViewById(R.id.itemImage);
        inf = findViewById(R.id.itemInf);
    }
}