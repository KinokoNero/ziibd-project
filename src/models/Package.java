package models;

public class Package {
    public Package(double weight, String contents) {
        this.weight = weight;
        this.contents = contents;
    }

    public int id;
    public double weight;
    public String contents;
    public DeliveryTruck deliveryTruck;

    public void assignToDeliveryTruck(DeliveryTruck deliveryTruck) {
        this.deliveryTruck = deliveryTruck;
    }
}
