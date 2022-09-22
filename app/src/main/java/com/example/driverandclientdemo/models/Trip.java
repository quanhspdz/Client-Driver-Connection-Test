package com.example.driverandclientdemo.models;

public class Trip {
    private String tripId;
    private String status;
    private String driverId;
    private String clientId;
    private Double distance;

    public Trip(String tripId, String status, String driverId, String clientId) {
        this.tripId = tripId;
        this.status = status;
        this.driverId = driverId;
        this.clientId = clientId;
    }

    public Trip() {
        this.tripId = "default";
        this.status = "default";
        this.driverId = "default";
        this.clientId = "default";
        this.distance = (double) -1;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripId='" + tripId + '\'' +
                ", status='" + status + '\'' +
                ", driverId='" + driverId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", distance=" + distance +
                '}';
    }
}
