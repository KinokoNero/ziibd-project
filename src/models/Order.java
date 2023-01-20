package models;

import java.sql.Date;

public class Order extends Model {
//    public Order(double price, String address, Date orderDate, String status) {
//        this.price = price;
//        this.address = address;
//        this.orderDate = orderDate;
//        this.status = status;
//    }

    public int clientId;
    public double price;
    public String address;
    public Date orderDate;
    public String status;
}
