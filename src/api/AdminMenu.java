package api;

import model.IRoom;
import model.Room;
import model.RoomType;
import service.CustomerService;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class AdminMenu {
    private static final AdminResource adminResource = AdminResource.getInstance();
    private static final CustomerService customerService = CustomerService.getInstance();
   // private static final ReservationService reservationService = ReservationService.getInstance();
    private static Scanner scanner;

    public static void printAdminMenu(){
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
        while(!quit){
            scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option){
                case 1:
                    displayAllCustomers();
                    printAdminMenu();
                    break;
                case 2:
                    printAllRooms();
                    printAdminMenu();
                    break;
                case 3:
                    displayAllReservations();
                    printAdminMenu();
                    break;
                case 4:
                    createIRoom();
                    printAdminMenu();
                    quit = true;
                    break;
                case 5:
                    break;
                case 6:
                    MainMenu.printMainMenu();
                    quit = true;
                    break;
            }
        }
    }

    private static void createIRoom(){
        ArrayList<IRoom> roomList = new ArrayList<>();
        boolean addAnotherRoom = true;
        while(addAnotherRoom){
            scanner = new Scanner(System.in);
            System.out.println("Enter room number");
            String roomNumber = scanner.nextLine();
            System.out.println("Enter price per night");
            double price = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter room type: 1 for single bed, 2 for double bed");
            int type = scanner.nextInt();
            if(type == 1){
                Room room = new Room(roomNumber,price,RoomType.SINGLE);
                roomList.add(room);
                System.out.println("Room " + roomNumber + " is added.");
            }else if(type == 2){
                Room room = new Room(roomNumber,price,RoomType.DOUBLE);
                roomList.add(room);
                System.out.println("Room " + roomNumber + " is added.");
            }
            System.out.println("Would you like to add another room y/n");
            String continueFlag = scanner.next().toUpperCase();
            if(continueFlag.equals("N")){
                addAnotherRoom = false;
            }
        }
        adminResource.addRoom(roomList);
    }

    private static void printAllRooms(){
        System.out.println(adminResource.getAllRooms());
    }

    private static void displayAllCustomers(){
        adminResource.getAllCustomers();
    }

    private static void displayAllReservations(){
        adminResource.displayAllReservations();
    }
}
