import java.sql.*;
import java.util.Scanner;

public class Main {


    public static void main(String[] args){
        try (Connection connection = DatabaseUtil.getConnection();
             Scanner scanner = new Scanner(System.in)) {
            Manipulations manipulations = new Manipulations();
            System.out.println("Connecting to database...");
            System.out.println("Connected to database successfully.");

            UserRegistrationService userService = new UserRegistrationService();

            boolean userLogIn = false;

            while (!userLogIn) {
                System.out.println("\n1. Register");
                System.out.println("2. Login");
                System.out.print("Enter your choice (1 or 2): ");
                int Choice = scanner.nextInt();
                scanner.nextLine();

                if (Choice == 1) {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    if (userService.registerUser(username, password)) {
                        System.out.println("Registration successful.");
                    } else {
                        System.out.println("Registration failed.");
                    }
                } else if (Choice == 2) {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    if (userService.loginUser(username, password)) {
                        System.out.println("Login successful.");
                        userLogIn = true;
                    } else {
                        System.out.println("Login failed. Please check your username and password.");
                    }
                }
            }
            if (userLogIn) {
                while (true) {
                    System.out.println("\n3. View all books");
                    System.out.println("4. Add a new book");
                    System.out.println("5. Search for a book by title");
                    System.out.println("6. Exit");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 3:
                            Manipulations.viewAllBooks();
                            break;
                        case 4:
                            manipulations.addNewBook();
                            break;
                        case 5:
                            manipulations.searchBookByTitle();
                            break;
                        case 6:
                            System.out.println("Exiting...");
                            return;
                        default:
                            System.out.println("Invalid choice, please try again.");
                    }
                }
            }
            Manipulations.viewAllBooks();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }




}
