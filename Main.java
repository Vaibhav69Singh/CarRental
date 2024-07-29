import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Car{
    private String carID;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay){
        this.carID = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }
    public String getCarID(){
        return carID;
    }

    public String getBrand(){
        return brand;
    }

    public String getModel(){
        return model;
    }
    public double calculatePrice(int rentalDays){
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void rent(){
        isAvailable = false;
    }
    public void returnCar(){
        isAvailable = true;
    }
}

class Customer{
    
    private String customerID;
    private String name;

    public Customer(String customerID, String name){
        this.customerID =customerID;
        this.name = name;
    }

    public String getCustomerID(){
        return customerID;
    }

    public String getName(){
        return name;
    }
}

class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar(){
        return car;
    }

    public Customer getCustomer(){
        return customer;
    }

    public int getDays(){
        return days;
    } 
}

class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCars(Car car){
        cars.add(car);
    }

    public void addCustomers(Customer customer){
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car, customer, days));
        }
        else{
            System.out.println("Sorry, the car you wanted for rental is currently unavailable");
        }
    }

    public void returnCar(Car car){
        Rental rentalToRemove = null;
        for(Rental rental : rentals){
            if(rental.getCar() == car){
                rentalToRemove = rental;
                break;
            }
        }
        if(rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            car.returnCar();
            System.out.println("Car is returned successfully");
        }else{
            System.out.println("Car was not rented");
        }
    }

    public void menu(){
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("\t***** Car Rental *****\t");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit ");
            System.out.println("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  //consume next line

            if(choice == 1 ){
                System.out.println("\n=== Rent a Car ==\n");
                System.out.println("Enter your name : ");
                String customerName = scanner.nextLine();
                
                System.out.println("\n Available Car : ");
                for(Car car : cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCarID() + " - " + car.getBrand() + " - " + car.getModel());
                    }
                }

            System.out.println("\nEnter the car ID you want to rent : ");
            String carID = scanner.nextLine();
            System.out.println("\nEnter the number of days for rental: ");
            int rentalDays = scanner.nextInt();
            scanner.nextLine();

            Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
            addCustomers(newCustomer);

            Car selectedCar = null;
            for(Car car : cars){
                if(car.getCarID().equals(carID) && car.isAvailable()){
                    selectedCar = car;
                    break; 
                }
            }

            if (selectedCar != null ){
                double totalPrice = selectedCar.calculatePrice(rentalDays);
                System.out.println("\n== Rental Informtaion ==\n");
                System.out.println("Customer ID : " + newCustomer.getCustomerID());
                System.out.println("Customer Name : "+ newCustomer.getName());
                System.out.println("Car : "+ selectedCar.getBrand() + " " + selectedCar.getModel());
                System.out.println("Rental Days : " + rentalDays);
                System.out.printf("Total price : $%.2f%n",totalPrice);

                System.out.printf("\nConfirm rental (Y/N) : ");
                String confirm = scanner.nextLine();

                if (confirm.equalsIgnoreCase("Y")){
                    rentCar(selectedCar, newCustomer, rentalDays);
                    System.out.println("\nCar rented successfully");
                }else{
                    System.out.println("Rental canceled");
                }
            }else{
                System.out.println("\nInvalid car selection or car not available for rent");
            }

            }else if (choice == 2){
                System.out.println("\n== Return a Car ==\n");
                System.out.println("Enter the car ID you want to return : ");
                String carID = scanner.nextLine();

                Car carToReturn = null;
                for(Car car : cars){
                    if(car.getCarID().equals(carID) && !car.isAvailable()){
                        carToReturn = car;
                        break;
                    }
                }

                if(carToReturn != null){
                    Customer customer = null;
                    for(Rental rental : rentals){
                        if(rental.getCar() == carToReturn){
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    
                    if(customer != null){
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by "+ customer.getName());
                    }else{
                        System.out.println("Car was not rented or rental information is missing");
                    }
                }else{
                    System.out.println("Invalid car ID or car is not rented");
                }
            }else if(choice == 3 ){
                break;
            }else{
                System.out.println("Invalid choice. Please enter a valid option");
            }
        }

        System.out.println("\nThankyou for using tha Car Rental System");
        scanner.close();
    }
}

public class Main{
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001", "Toyota", "Supra", 20000);
        Car car2 = new Car("C002","Mahindra", "Thar", 2000);
        Car car3 = new Car("C003", "Tata", "Harrier", 1500);
        Car car4 =  new Car("C004","Suzuki", "Swift", 800);
        rentalSystem.addCars(car1);
        rentalSystem.addCars(car2);
        rentalSystem.addCars(car3);
        rentalSystem.addCars(car4);
        rentalSystem.menu();
    }
}