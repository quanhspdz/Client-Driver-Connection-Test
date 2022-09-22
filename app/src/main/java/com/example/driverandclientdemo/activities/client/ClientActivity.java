package com.example.driverandclientdemo.activities.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.driverandclientdemo.R;
import com.example.driverandclientdemo.activities.driver.ClientFoundActivity;
import com.example.driverandclientdemo.activities.driver.DriverActivity;
import com.example.driverandclientdemo.models.Client;
import com.example.driverandclientdemo.models.Driver;
import com.example.driverandclientdemo.models.Trip;
import com.example.driverandclientdemo.utilities.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientActivity extends AppCompatActivity {

    Button btnSearchDriver, btnCancel, btnCreateNewTrip;
    Trip trip;
    Client client;
    boolean isSearching, driverFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        btnSearchDriver = findViewById(R.id.btn_searchDriver);
        btnCancel = findViewById(R.id.btn_cancel);
        btnCreateNewTrip = findViewById(R.id.btn_createNewTrip);

        isSearching = false;
        driverFound = false;

        client = new Client();
        client.setClientId("1");
        client.setClientName("Quang Anh");

        trip = new Trip();
        trip.setTripId("1");
        trip.setDistance((double) 69);
        trip.setClientId(client.getClientId());
        trip.setStatus(Constants.searchingDriver);
        updateTripDriver(new Driver());


        setListener();

    }

    private void setListener() {
        btnSearchDriver.setOnClickListener(v -> {
            searchForDriver();
            isSearching = true;
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTrip(trip.getTripId());
            }
        });

        btnCreateNewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTrip(trip);
            }
        });
    }

    private void cancelTrip(String tripId) {
        FirebaseDatabase.getInstance(Constants.firebaseRefUrl).getReference()
                .child("Trips")
                .child(tripId)
                .removeValue();
    }

    private void searchForDriver() {
        if (!isSearching) {
            FirebaseDatabase.getInstance(Constants.firebaseRefUrl).getReference()
                    .child("Drivers")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!driverFound) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Driver driver = dataSnapshot.getValue(Driver.class);
                                    assert driver != null;
                                    if (driver.getStatus().equals("available")
                                        && !(driver.getDriverId().equals("-1"))) {
                                        Toast.makeText(ClientActivity.this, driver.getDriverId(), Toast.LENGTH_SHORT).show();
                                        updateTripDriver(driver);
                                        updateTripStatus(Constants.waitingDriver);
                                        driverFound = true;
                                        moveToDriverFoundActivity();
                                        break;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
    }

    private void moveToDriverFoundActivity() {
        Intent intent = new Intent(ClientActivity.this, ClientFoundActivity.class);
        intent.putExtra("tripId", trip.getTripId());
        startActivity(intent);
        finish();
    }

    private void updateTripDriver(Driver driver) {
        FirebaseDatabase.getInstance(Constants.firebaseRefUrl)
                .getReference()
                .child("Trips")
                .child(trip.getTripId())
                .child("driverId")
                .setValue(driver.getDriverId());
    }

    private void createNewTrip(Trip trip) {
        FirebaseDatabase.getInstance(Constants.firebaseRefUrl)
                .getReference()
                .child("Trips")
                .child(trip.getTripId())
                .setValue(trip);
    }
    private void updateTripStatus(String status) {
        FirebaseDatabase.getInstance(Constants.firebaseRefUrl)
                .getReference()
                .child("Trips")
                .child(trip.getTripId())
                .child("status")
                .setValue(status);
    }
}