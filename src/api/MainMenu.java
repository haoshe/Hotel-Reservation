package api;

import api.AdminMenu;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;
import utilities.Utilities;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        start();
    }

    public static void start() throws ParseException{
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
        boolean quit = false;
        int option;
        while(!quit){
            option = scanner.nextInt();
            scanner.nextLine();
            switch(option){
                case 1:
                    findARoom();
                    start();
                    break;
                case 2:
                    displayCustomerReservation();
                     start();
                    break;
                case 3:
                    createAccount();
                     start();
                    break;
                case 4:
                    AdminMenu.start();
                    break;
                case 5:
                   // break outerLoop;
                    System.out.println("logging out...");
                    System.exit(0);
                    quit = true;
                    break;
                default:
                    System.out.println("choice must be a number between 1 to 5.");
                    break;
            }
        }
    }

    private static void createAccount(){
        String email;
        while(true){
            System.out.println("Enter Email format: name@domain.com");
            String emailInput = scanner.nextLine();
            if(Utilities.isEmailValid(emailInput)){
                if (Utilities.hasEmailRegistered(emailInput)) {
                    System.out.println("This email has already been registered.");
                    continue;
                }else{
                    email = emailInput;
                    break;
                }
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


    private static void findARoom() throws ParseException {
        Date checkInDate;
        Date checkOutDate;
        boolean continueFlag = true;
        while(continueFlag){

            while(true){
                System.out.println("Enter CheckIn Date mm/dd/yyyy example 02/01/2020");
                String checkInDateInput = scanner.nextLine();
                boolean isCheckInDateValid = Utilities.isDateValid(checkInDateInput);
                if(isCheckInDateValid){
                    Date checkedDate = new SimpleDateFormat("MM/dd/yyyy").parse(checkInDateInput);
                    if(Utilities.isCheckInDateValid(checkedDate)){
                        checkInDate = checkedDate;
                        break;
                    }else{
                        System.out.println("Please enter a date no earlier than today.");
                    }
                }else{
                    System.out.println("Please enter correct date format.");
                }
            }

            while(true){
                System.out.println("Enter CheckOut Date mm/dd/yyyy example 02/01/2020");
                String checkOutDateInput = scanner.nextLine();
                boolean isCheckOutDateValid = Utilities.isDateValid(checkOutDateInput);
                if(isCheckOutDateValid){
                    Date checkedDate = new SimpleDateFormat("MM/dd/yyyy").parse(checkOutDateInput);
                    if(Utilities.isCheckOutDateValid(checkInDate,checkedDate)){
                        checkOutDate = checkedDate;
                        break;
                    }else{
                        System.out.println("Please enter a date later than check in date.");
                    }
                }else{
                    System.out.println("Please enter correct date format.");
                }
            }

            if(Utilities.isRoomAvailable(checkInDate,checkOutDate)){
                Utilities.displayAvailableRooms(checkInDate,checkOutDate);
                reserveARoom(checkInDate,checkOutDate);
                continueFlag = false;
            }else{
                System.out.println("There is no room available within these dates.");
                String option;
                while(true){
                    System.out.println("Would you like to choose another date y/n");
                    option = scanner.next().toUpperCase();
                    if(!option.equals("Y") && !option.equals("N")){
                        System.out.println("Please enter Y or N.");
                    }else{
                        break;
                    }
                }

                if(option.equals("N")){
                    continueFlag = false;
                }else if(option.equals("Y")){
                    continue;
                }
            }
        }
    }


    private static void reserveARoom(Date checkInDate, Date checkOutDate) throws ParseException {

        boolean quit = false;
        outerLoop:
        while(!quit){
            String permissionFlag;
            while(true){
                System.out.println("Would you like to book a room? y/n");
                String choice = scanner.next().toUpperCase();
                if(Utilities.isYesOrNoValid(choice)){
                    permissionFlag = choice;
                    break;
                }else{
                    System.out.println("Please type Y or N");
                }
            }

            if(permissionFlag.equals("N")){
                quit = true;
            }
            if(permissionFlag.equals("Y")){
                String haveAccountFlag;
                while(true){
                    System.out.println("Do you have an account with us? y/n");
                    String choice = scanner.next().toUpperCase();
                    if(Utilities.isYesOrNoValid(choice)){
                        haveAccountFlag = choice;
                        break;
                    }else{
                        System.out.println("Please type Y or N");
                    }
                }

                if(haveAccountFlag.equals("Y")){

                    String email = "";
                    while(true){
                        System.out.println("Enter Email format: name@domain.com");
                        scanner.nextLine();
                        String  emailInput = scanner.nextLine();

                        if(Utilities.isEmailValid(emailInput) && hotelResource.getCustomer(emailInput) != null){
                            email = emailInput;
                            break;
                        }else if(hotelResource.getCustomer(emailInput) == null){
                            System.out.println("The email is not registered, please create an account before reserve a room.");
                            break outerLoop;
                        }else if(!Utilities.isEmailValid(emailInput)){
                            System.out.println("Please enter a valid email format.");
                        }
                    }

                    IRoom room;
                   while(true){
                       System.out.println("Please type room number you would like to reserve:");
                       String roomNumber = scanner.nextLine();
                       // check if the room number typed in matches the available room numbers
                       if(Utilities.doesRoomNumberExist(roomNumber)){
                           room = hotelResource.getRoom(roomNumber);
                           break;
                       }else{
                           System.out.println("Please type in available room number.");
                       }
                   }
                   Reservation reservedRoom = hotelResource.bookARoom(email,room,checkInDate,checkOutDate);

                   System.out.println("Your booking details are as follows: ");
                   System.out.println(reservedRoom);
                   quit = true;

                }else if(haveAccountFlag.equals("N")){
                    System.out.println("Please create an account before reserve a room.");
                    break outerLoop;
                }
            }
        }
    }



    private static void displayCustomerReservation(){
        String email = "";
        while (true) {
            System.out.println("Enter Email format: name@domain.com");
            String  emailInput = scanner.nextLine();
            if(Utilities.isEmailValid(emailInput) && hotelResource.getCustomer(emailInput) != null){
                email = emailInput;
                break;
            }else if(hotelResource.getCustomer(emailInput) == null){
                System.out.println("You haven't got an account with us.");
                break;
            }else if(!Utilities.isEmailValid(emailInput)){
                System.out.println("Please enter a valid email format.");
            }
        }
        Reservation reservation = hotelResource.getCustomerReservations(email);
        System.out.println("Your reservation details as follows: ");
        System.out.println(reservation);
    }
}
