import java.util.*;

class Car {
    private String carId;
    private String model;
    private boolean available;

    public Car(String carId, String model) {
        this.carId = carId;
        this.model = model;
        this.available = true;
    }

    public String getCarId() { return carId; }
    public String getModel() { return model; }
    public boolean isAvailable() { return available; }
    public void rentCar() { available = false; }
    public void returnCar() { available = true; }

    @Override
    public String toString() {
        return carId + " - " + model + " (Available: " + available + ")";
    }
}

// Customer class
class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return customerId + " - " + name;
    }
}

class RentalTransaction {
    private Car car;
    private Customer customer;
    private Date date;

    public RentalTransaction(Car car, Customer customer) {
        this.car = car;
        this.customer = customer;
        this.date = new Date();
    }

    public String toString() {
        return customer.getName() + " rented " + car.getModel() + " on " + date;
    }
}

class RentalAgency {
    private List<Car> cars = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<RentalTransaction> transactions = new ArrayList<>();

    public void addCar(Car car) { cars.add(car); }
    public void addCustomer(Customer customer) { customers.add(customer); }

    public void rentCar(String carId, String customerId) {
        Car car = findCar(carId);
        Customer customer = findCustomer(customerId);
        if (car != null && customer != null && car.isAvailable()) {
            car.rentCar();
            RentalTransaction rt = new RentalTransaction(car, customer);
            transactions.add(rt);
            System.out.println("Car rented successfully: " + rt);
        } else {
            System.out.println("Car not available or wrong details.");
        }
    }

    public void returnCar(String carId) {
        Car car = findCar(carId);
        if (car != null && !car.isAvailable()) {
            car.returnCar();
            System.out.println("Car returned successfully.");
        } else {
            System.out.println("Invalid return.");
        }
    }

    private Car findCar(String id) {
        for (Car c : cars) if (c.getCarId().equals(id)) return c;
        return null;
    }

    private Customer findCustomer(String id) {
        for (Customer c : customers) if (c.getCustomerId().equals(id)) return c;
        return null;
    }

    public void showCars() { cars.forEach(System.out::println); }
    public void showTransactions() { transactions.forEach(System.out::println); }
}

// Main Class
public class CarRentalApp {
    public static void main(String[] args) {
        RentalAgency agency = new RentalAgency();

        
        agency.addCar(new Car("C1", "Toyota Corolla"));
        agency.addCar(new Car("C2", "Honda Civic"));
        agency.addCustomer(new Customer("U1", "Alice"));
        agency.addCustomer(new Customer("U2", "Bob"));
    }
}