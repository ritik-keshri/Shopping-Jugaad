
package com.example.shoppingjugaad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegLogChoice extends AppCompatActivity {
    Button reg,log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_log_choice);
        reg= findViewById(R.id.btnreg);
        log= findViewById(R.id.btnlog);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegLogChoice.this, LoginPage.class);
                startActivity(intent);
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegLogChoice.this, RegisterPage.class);
                startActivity(intent);
            }
        });
    }
    public void onBackPressed(){
     moveTaskToBack(true);
     android.os.Process.killProcess(android.os.Process.myPid());
     System.exit(1);
    }
}
