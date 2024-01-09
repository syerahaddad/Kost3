
package com.example.kost;
public class Order {
    private String email;
    private String roomType;
    private String tanggal;
    private String totalTagihan;
    public Order() {
    }

    public Order(String email, String roomType, String tanggal, String totalTagihan) {
        this.email = email;
        this.roomType = roomType;
        this.tanggal = tanggal;
        this.totalTagihan = totalTagihan;
    }

    public String getEmail() {
        return email;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getTotalTagihan() {
        return totalTagihan;
    }
}
