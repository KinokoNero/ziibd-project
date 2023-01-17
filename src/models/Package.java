package models;

public class Package extends Model {
    public Package(double weight, String contents) {
        this.weight = weight;
        this.contents = contents;
    }

    public double weight;
    public String contents;
}
