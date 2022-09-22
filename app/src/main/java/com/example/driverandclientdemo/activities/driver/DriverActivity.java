package com.example.driverandclientdemo.activities.driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.driverandclientdemo.Fragments.driver.ClientFoundFragment;
import com.example.driverandclientdemo.R;
import com.example.driverandclientdemo.models.Driver;
import com.example.driverandclientdemo.models.Trip;
import com.example.driverandclientdemo.utilities.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverActivity extends AppCompatActivity {

    SwitchCompat switchAvailable;
    EditText edtDistance, edtDriverId;
    Button btnUpdateDistance;
    Driver driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        switchAvailable = findViewById(R.id.switch_available);
        edtDistance = findViewById(R.id.edt_distance);
        btnUpdateDistance = findViewById(R.id.btnUpdateDistance);
        edtDriverId = findViewById(R.id.edt_driverId);

        driver = new Driver();
        driver.setDriverId("-1");

        setListener();

        setNewTripListener();

    }

    private void setNewTripListener() {
        FirebaseDatabase.getInstance(Constants.firebaseRefUrl)
                .getReference()
                .child("Trips")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Trip trip = dataSnapshot.getValue(Trip.class);
                            assert trip != null;
                            if (trip.getDriverId().equals(driver.getDriverId())) {
                                Toast.makeText(DriverActivity.this, trip.getTripId(), Toast.LENGTH_SHORT).show();
                                moveToClientFoundActivity(trip);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void moveToClientFoundActivity(Trip trip) {
        Intent intent = new Intent(DriverActivity.this, ClientFoundActivity.class);
        intent.putExtra("tripId", trip.getTripId());
        startActivity(intent);
        finish();
    }


    private void updateCurrentDriverInfo() {
        driver = new Driver();
        driver.setDriverName("Quang Em");

        if (edtDriverId.getText().toString().isEmpty()) {
            driver.setDriverId("-1");
        } else {
            driver.setDriverId(edtDriverId.getText().toString());
        }
        FirebaseDatabase.getInstance(Constants.firebaseRefUrl).getReference()
                .child("Drivers")
                .child(driver.getDriverId())
                .setValue(driver);
    }

    private void setListener() {
        switchAvailable.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                driver.setStatus("available");
            } else {
                driver.setStatus("unavailable");
            }
            updateDriverStatus(driver);
        });

        btnUpdateDistance.setOnClickListener(v -> {
//            Double newDistance = null;
//            if (!(edtDistance.getText().toString().isEmpty()))
//                newDistance = Double.parseDouble(edtDistance.getText().toString());
//            if (newDistance != null) {
//                driver.setDistance(newDistance);
//                updateDriverDistance(driver);
//            }
            updateDriverDistance(driver);
        });
    }

    private void updateDriverDistance(Driver driver) {
        updateCurrentDriverInfo();
        setNewTripListener();
//        FirebaseDatabase.getInstance(Constants.firebaseRefUrl).getReference()
//                .child("Drivers")
//                .child(driver.getDriverId())
//                .child("distance")
//                .setValue(driver.getDistance());
    }

    private void updateDriverStatus(Driver driver) {
        FirebaseDatabase.getInstance(Constants.firebaseRefUrl).getReference()
                .child("Drivers")
                .child(driver.getDriverId())
                .child("status")
                .setValue(driver.getStatus());
    }


    @Override
    protected void onPause() {
        super.onPause();
        driver.setStatus("unavailable");
        updateDriverStatus(driver);
    }
}