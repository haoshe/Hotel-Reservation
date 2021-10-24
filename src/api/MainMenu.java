package api;

import api.AdminMenu;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.*;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

public class MainMenu {
    private static Scanner scanner = new Scanner(System.in);
    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();
    private static final DateTimeFormatter dateFormat = new DateTimeFormatterBuilder()
            .parseStrict()
            .appendPattern("MM/dd/uuuu")
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);

    public static void main(String[] args) throws ParseException {
        printMainMenu();

        boolean quit = false;
        while(!quit){
            int option = scanner.nextInt();
            scanner.nextLine();
            switch(option){
                case 1:
                    reserveARoom();
                    printMainMenu();
                    break;
                case 2:
                    displayCustomerReservation();
                    printMainMenu();
                    break;
                case 3:
                    createAccount();
                    printMainMenu();
                    break;
                case 4:
                    AdminMenu.printAdminMenu();
                    break;
                case 5:
                    quit = true;
                    break;
            }
        }
    }

    public static void mainMenu() throws ParseException {
        boolean quit = false;
        while(!quit){
            int option = scanner.nextInt();
            scanner.nextLine();
            switch(option){
                case 1:
                    reserveARoom();
                    printMainMenu();
                    break;
                case 2:
                    displayCustomerReservation();
                    printMainMenu();
                    break;
                case 3:
                    createAccount();
                    printMainMenu();
                    break;
                case 4:
                    AdminMenu.printAdminMenu();
                    break;
                case 5:
                    quit = true;
                    break;
            }
        }
    }
    public static void printMainMenu() throws ParseException {
        System.out.println();
        System.out.println(
                "Welcome to the Hotel Reservation Application\n\n" +
                "__________________________________________________\n" +
                "1. Find and reserve a room\n" +
                "2. See my reservations\n" +
                "3. Create an account\n" +
                "4. Admin\n" +
                "5. Exit\n" +
                "---------------------------------------------------\n" +
                "Please select a number for the menu option\n");
    }

    private static void createAccount(){
        String email = "";
        while(true){
            System.out.println("Enter Email format: name@domain.com");
            String emailInput = scanner.nextLine();
            if(isEmailValid(emailInput)){
                email = emailInput;
                break;
            }else{
                System.out.println("Please type in correct email format.");
            }
        }
        System.out.println("First Name:");
        String firstName = scanner.nextLine();

        System.out.println("Last Name:");
        String lastName = scanner.nextLine();

        hotelResource.createACustomer(email,firstName,lastName);
        System.out.println("New account has been created successfully.");
    }

    private static boolean isEmailValid(String email){
        String emailRegEx = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailRegEx);
        if(!pattern.matcher(email).matches()){
            return false;
        }
        return true;
    }

    private static void reserveARoom() throws ParseException {
        Date checkInDate;

        while(true){
            System.out.println("Enter CheckIn Date mm/dd/yyyy example 02/01/2020");
            String strDate = scanner.nextLine();
            boolean isValid = isDateValid(strDate);
            if(isValid){
                 checkInDate = new SimpleDateFormat("MM/dd/yyyy").parse(strDate);
                 break;
            }else{
                System.out.println("Please enter correct date format.");
            }
        }

        Date checkOutDate;
        while(true){
            System.out.println("Enter CheckOut Date mm/dd/yyyy example 02/01/2020");
            String strDate = scanner.nextLine();
            boolean isValid = isDateValid(strDate);
            if(isValid){
                checkOutDate = new SimpleDateFormat("MM/dd/yyyy").parse(strDate);
                break;
            }else{
                System.out.println("Please enter correct date format.");
            }
        }
        Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate,checkOutDate);
        for(IRoom availableRoom : availableRooms){
            System.out.println(availableRoom);
        }

        boolean quit = false;

        while(!quit){
            System.out.println("Would you like to book a room? y/n");// add a method to check valid y/n input later
            String permissionFlag = scanner.next().toUpperCase();
            if(permissionFlag.equals("N")){
                printMainMenu();
                break;
            }
            if(permissionFlag.equals("Y")){
                System.out.println("Do you have an account with us? y/n");// add a method to check valid y/n
                String haveAccountFlag = scanner.next().toUpperCase();
                if(haveAccountFlag.equals("Y")){
                    String email = "";
                    while(true){
                        System.out.println("Enter Email format: name@domain.com");
                        scanner.nextLine();
                        String  emailInput = scanner.nextLine();

                        if(isEmailValid(emailInput) && hotelResource.getCustomer(emailInput) != null){
                            email = emailInput;
                            break;
                        }else if(hotelResource.getCustomer(emailInput) == null){
                            System.out.println("Please create an account before reserve a room.");
                            quit = true;
                            break;
                        }else if(!isEmailValid(emailInput)){
                            System.out.println("Please enter a valid email format.");
                        }
                    }
                    IRoom room;
                   while(true){
                       System.out.println("Please type room number you would like to reserve:");
                       // method to check valid room number
                       String roomNumber = scanner.nextLine();
                       if(isRoomNumberValid(roomNumber)){
                           room = hotelResource.getRoom(roomNumber);
                           quit = true;
                           break;
                       }else{
                           System.out.println("Please type in available room number.");
                       }
                   }
                   Reservation reservedRoom = hotelResource.bookARoom(email,room,checkInDate,checkOutDate);
                   System.out.println("Your booking details are as follows: ");
                   System.out.println(reservedRoom);
                }
                if(haveAccountFlag.equals("N")){
                    System.out.println("Please create an account before reserve a room.");
                    break;
                }
            }
        }
    }

    // find out if the input room number is in the available rooms list
   private static boolean isRoomNumberValid(String number){
        Set<IRoom> availableRooms = reservationService.findAvailableRooms();
       for(IRoom availableRoom : availableRooms){
           if(availableRoom.getRoomNumber().equals(number)){
               return true;
           }
       }
       return false;
   }

    private static boolean isDateValid(String date){
        try{
            LocalDate.parse(date,dateFormat);
        }catch (DateTimeParseException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private static void displayCustomerReservation(){
        String email = "";
        while (true) {
            System.out.println("Enter Email format: name@domain.com");
            String  emailInput = scanner.nextLine();
            if(isEmailValid(emailInput) && hotelResource.getCustomer(emailInput) != null){
                email = emailInput;
                break;
            }else if(hotelResource.getCustomer(emailInput) == null){
                System.out.println("You haven't got an account with us.");
                break;
            }else if(!isEmailValid(emailInput)){
                System.out.println("Please enter a valid email format.");
            }
        }
        Reservation reservation = hotelResource.getCustomerReservations(email);
        System.out.println("Your reservation details as follows: ");
        System.out.println(reservation);
    }

}
