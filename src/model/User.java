package model;

/**
 * Represents a registered user of the Turf Booking system.
 * Demonstrates Encapsulation - all fields are private with getters/setters.
 */
public class User {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private int age;
    private String password;

    // Constructor
    public User(String userId, String name, String email, String phoneNumber, int age, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.password = password;
    }

    // Getters and Setters (Encapsulation)
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    @Override
    public String toString() {
        return String.format("User Profile \n " +
                              "Name : " + name + "\n" +
                              "Email : " + email + "\n" +
                              "Phone : "+ phoneNumber + "\n" +
                              "Age : " + age);
    }
}
