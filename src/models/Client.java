package models;

import menu.DatabaseManager;

public class Client extends Model {
    public Client(String firstName, String lastName, String phoneNumber, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String email;
    public String address;
}
