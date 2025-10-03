import java.io.Console;
import java.util.Scanner;

public class LoginSystem {
    public static void main(String[] args) {
        String correctUsername = "admin";
        String correctPassword = "password123";
        int attempts = 0;
        boolean loggedIn = false;

        Scanner sc = new Scanner(System.in);
        Console console = System.console();

        while (attempts < 3) {
            System.out.print("Enter Username: ");
            String username = sc.nextLine();

            String password;
            if (console != null) {
                char[] passArray = console.readPassword("Enter Password: ");
                password = new String(passArray);
            } else {
                System.out.print("Enter Password: ");
                password = sc.nextLine();
                // Mask simulation
                StringBuilder stars = new StringBuilder();
                for (int i = 0; i < password.length(); i++) stars.append("*");
                System.out.println("Password Entered: " + stars);
            }

            if (username.equals(correctUsername) && password.equals(correctPassword)) {
                System.out.println("Login Successful ✅");
                loggedIn = true;
                break;
            } else {
                System.out.println("Invalid credentials ❌");
                attempts++;
            }
        }

        if (loggedIn) {
            System.out.println("Access Denied 🚫");
        }

        sc.close();
    }
}