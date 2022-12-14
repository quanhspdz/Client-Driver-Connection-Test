package com.example.driverandclientdemo.models;

public class Client {
    private String clientId;
    private String clientName;

    public Client(String clientId, String clientName) {
        this.clientId = clientId;
        this.clientName = clientName;
    }

    public Client() {
        this.clientId = "default";
        this.clientName = "default";
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
