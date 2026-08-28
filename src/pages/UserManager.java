package pages;


import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users; // List to store all users (Med and Pat)

    // Constructor to initialize the list
    public UserManager() {
        this.users = new ArrayList<>();
    }

    // Method to add a user (either Med or Pat)
    public void addUser(User user) {
        users.add(user);
    }

    // Method to remove a user by username
    public boolean removeUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                users.remove(user);
                return true;
            }
        }
        return false; // User not found
    }

    // Method to get a user by username
    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // User not found
    }

    // Method to get all users (for displaying or processing)
    public List<User> getAllUsers() {
        return users;
    }

    // Method to check if a user exists by username
    public boolean userExists(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false; // User does not exist
    }

    // Method to display all users (optional, for debugging purposes)
    public void displayUsers() {
        for (User user : users) {
            System.out.println("Username: " + user.getUsername());
            System.out.println("Full Name: " + user.getFullName());
            System.out.println("Gender: " + user.getGender());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Phone: " + user.getPhone());
            System.out.println("----");
        }
    }
}
