package models;

import java.util.Date;

public class Order {
    public Order(double price, String address, Date orderDate, String status) {
        this.price = price;
        this.address = address;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int id;
    public double price;
    public String address;
    public Date orderDate;
    public String status;
}
