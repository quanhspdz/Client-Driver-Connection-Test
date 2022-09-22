package com.example.driverandclientdemo.models;

public class Driver{
    private String driverId;
    private String driverName;
    private String status;
    private Double distance;

    public Driver(String driverId, String driverName, String status, Double distance) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.status = status;
        this.distance = distance;
    }

    public Driver() {
        this.driverId = "default";
        this.driverName = "default";
        this.distance = (double) 0;
        this.status = "default";
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
