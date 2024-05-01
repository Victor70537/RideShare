package edu.uga.cs.rideshare;


/**
 * This class represents a single ride, including the rider,
 * driver, destination, and some comments.
 */
public class Ride {
    private String key;
    private String userId;
    private String rider;
    private String driver;
    private String phone;
    private String destination;
    private String comments;

    public Ride()
    {
        this.key = null;
        this.userId = null;
        this.rider = null;
        this.driver = null;
        this.phone = null;
        this.destination = null;
        this.comments = null;
    }

    public Ride(String rider, String driver, String phone, String destination, String comments ) {
        this.key = null;
        this.userId = null;
        this.rider = rider;
        this.driver = driver;
        this.phone = phone;
        this.destination = destination;
        this.comments = comments;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRider() {
        return rider;
    }

    public void setRider(String rider) {
        this.rider = rider;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String toString() {
        return rider + " " + driver + " " + phone + " " + destination + " " + comments;
    }
}
