package com.example.driverandclientdemo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.driverandclientdemo.R;
import com.example.driverandclientdemo.activities.client.ClientActivity;
import com.example.driverandclientdemo.activities.driver.DriverActivity;

public class StartActivity extends AppCompatActivity {

    Button btnClient, btnDriver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnClient = findViewById(R.id.btnClient);
        btnDriver = findViewById(R.id.btnDriver);

        setListener();
    }

    private void setListener() {
        btnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, DriverActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, ClientActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}