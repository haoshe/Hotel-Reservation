package api;

import model.*;
import service.CustomerService;
import service.ReservationService;

import java.text.ParseException;
import java.util.*;

import utilities.*;

public class AdminMenu {
    private static final AdminResource adminResource = AdminResource.getInstance();
    private static final CustomerService customerService = CustomerService.getInstance();
    private static Scanner scanner;

    public static void start() throws ParseException {
        System.out.println(
                "\nAdmin Menu\n" +
                "--------------------------------------------\n" +
                "1. See all Customers\n" +
                "2. See all Rooms\n" +
                "3. See all Reservations\n" +
                "4. Add a Room\n" +
                "5. Add Test Data\n" +
                "6. Back to Main Menu\n" +
                "--------------------------------------------\n" +
                "Please select a number for the menu option\n"
        );
        boolean quit = false;
        int option;
        while(!quit){

            while(true){
                Scanner scanner = new Scanner(System.in);
                boolean isInt = scanner.hasNextInt();
                if(isInt){
                    option = scanner.nextInt();
                    scanner.nextLine();
                    break;
                }
                option = 7;// if input is not a int, assign a number larger than 6, so the default case can catch it.
                scanner.nextLine();
                break;
            }

            switch (option){
                case 1:
                    displayAllCustomers();
                    start();
                    break;
                case 2:
                    printAllRooms();
                    start();
                    break;
                case 3:
                    displayAllReservations();
                    start();
                    break;
                case 4:
                    createIRoom();
                    start();
                    quit = true;
                    break;
                case 5:
                    System.out.println("I don't know what this option for.");
                    start();
                    break;
                case 6:
                    quit = true;
                    MainMenu.start();
                    break;
                default:
                    System.out.println("Choice must be a number between 1 to 6.");
                    break;
            }
        }
    }

    private static void createIRoom(){

        boolean addAnotherRoom = true;

        while(addAnotherRoom){
            scanner = new Scanner(System.in);

            // aks user to type in room number, check if it is valid and not already in the room list.
            String roomNumberInput;
            String roomNumber;
            while(true){
                System.out.println("Enter room number");
                boolean isInt = scanner.hasNextInt();
                if(isInt){
                    int numberInput = scanner.nextInt();
                    scanner.nextLine();
                    roomNumberInput = String.valueOf(numberInput);
                }else{
                    // handle end of line(enter key).
                    scanner.nextLine();
                    continue;
                }

                if(Utilities.isRoomNumberValid(roomNumberInput)){
                    if(Utilities.doesRoomExist(roomNumberInput)){
                        System.out.println("The room is already in the system, please enter different room number:");
                    }else{
                        roomNumber = roomNumberInput;
                        break;
                    }
                }else{
                    System.out.println("Please enter a room number between 100 and 1000.");
                }
            }

            // ask user to type in the room price and check if it is valid.
            double price = 0.0;
            while(true){
                System.out.println("Enter price per night");
                boolean isDouble = scanner.hasNextDouble();
                if(isDouble){
                    price = scanner.nextDouble();
                    break;
                }else{
                    scanner.nextLine();
                    System.out.println("Please enter a valid price.");
                }
            }

            // check if the type input is valid.
            int type = 0;
            while(true){
                System.out.println("Enter room type: 1 for single bed, 2 for double bed");
                boolean isInt = scanner.hasNextInt();
                if(isInt) {
                    int intInput = scanner.nextInt();
                    scanner.nextLine();
                    if(intInput==1 || intInput==2){
                        type = intInput;
                        break;
                    }else{
                        System.out.println("please choose 1 or 2 only");
                        continue;
                    }
                }else if(!isInt){
                    System.out.println("Please type in a valid number.");
                }
                scanner.nextLine();
            }

            if(type == 1){
                IRoom room = new Room(roomNumber,price,RoomType.SINGLE);
                adminResource.addRoom(room);
                System.out.println("Room " + roomNumber + " is added.");
            }else if(type == 2){
                IRoom room = new Room(roomNumber,price,RoomType.DOUBLE);
                adminResource.addRoom(room);
                System.out.println("Room " + roomNumber + " is added.");
            }

            // check if the input y/n is valid.
            String continueFlag;
            while(true){
                System.out.println("Would you like to add another room y/n");
                continueFlag = scanner.next().toUpperCase();
                //System.out.println(continueFlag);
                if(!continueFlag.equals("Y") && !continueFlag.equals("N")){
                    System.out.println("Please enter Y or N.");
                }else{
                    break;
                }
            }

            if(continueFlag.equals("N")){
                addAnotherRoom = false;
            }else if(continueFlag.equals("Y")){
                continue;
            }
        }
    }

    private static void printAllRooms(){
        //System.out.println(adminResource.getAllRooms());
        if(adminResource.getAllRooms().isEmpty()){
            System.out.println("There are currently no rooms in the database.");
            return;
        }
        Collection<IRoom> rooms = adminResource.getAllRooms();
        int counter = 1;
        for(IRoom room : rooms){
            System.out.println(counter + "." + " " + room);
            counter++;
        }
    }

    private static void displayAllCustomers(){
        if(adminResource.getAllCustomers().isEmpty()){
            System.out.println("There are currently no customers in the database.");
            return;
        }
        Collection<Customer> customers = adminResource.getAllCustomers();
        int counter = 1;
        for(Customer customer : customers){
            System.out.println(counter + "." + " " + customer);
            counter++;
        }
    }

    private static void displayAllReservations(){
        if(adminResource.getAllReservations().isEmpty()){
            System.out.println("There are currently no reservations in the database.");
            return;
        }
        Set<Reservation> reservations = adminResource.getAllReservations();
        int counter = 1;
        for(Reservation reservation : reservations){
            System.out.println(counter + "." + " " + reservation);
            counter++;
        }

    }
}
