package models;

import java.sql.Date;

public class Order extends Model {
    public int clientId;
    public double price;
    public String address;
    public Date orderDate;
    public String status;
}
