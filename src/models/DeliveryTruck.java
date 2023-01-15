package models;

public class DeliveryTruck {
    public DeliveryTruck(String model, double load, String status) {
        this.model = model;
        this.load = load;
        this.status = status;
    }

    public int id;
    public String model;
    public double load; //load capacity in kg
    public String status;
}
