package utilities;

import api.AdminResource;
import api.HotelResource;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

public class Utilities {
    private static final AdminResource adminResource = AdminResource.getInstance();
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();
    // get it from Udacity mentor help Q&A platform
    private static final DateTimeFormatter dateFormat = new DateTimeFormatterBuilder()
            .parseStrict()
            .appendPattern("MM/dd/uuuu")
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);


    // when adding a room to the hotel rooms list, check if the input room number has already existed in the list.
    public static boolean doesRoomExist(String roomNumber){
        Collection<IRoom> rooms = adminResource.getAllRooms();
        for(IRoom room : rooms){
            if(roomNumber.equals(room.getRoomNumber())){
                return true;
            }
        }
        return false;
    }

    // when user types in a room number, check if the number is a valid room number.
    // let's suppose the hotels' room numbers are from 100 to 1000, the room number input has to be in this range.
    public static boolean isRoomNumberValid(String roomNumber){

        int number = Integer.parseInt(roomNumber);
        if(number >= 100 && number <= 1000){
            return true;
        }
        return false;
    }

    // check if email is valid
    public static boolean isEmailValid(String email){
        String emailRegEx = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(emailRegEx);
        if(!pattern.matcher(email).matches()){
            return false;
        }
        return true;
    }

    // check if the email has already been registered.
    public static boolean hasEmailRegistered(String email){
        if(hotelResource.getCustomer(email)!=null){
            return true;
        }
        return false;
    }

    // find out if the input room number is in the available rooms list
    public static boolean doesRoomNumberExist(String number, Date checkIn, Date checkOut){
        Collection<IRoom> availableRooms = reservationService.findAvailableRooms(checkIn,checkOut);
        for(IRoom availableRoom : availableRooms){
            if(availableRoom.getRoomNumber().equals(number)){
                return true;
            }
        }
        return false;
    }

    // check the input date is the same format as required.
    public static boolean isDateValid(String date){
        try{
            LocalDate.parse(date,dateFormat);
        }catch (DateTimeParseException e){
           // System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    // display all rooms available judging by check in and check out dates.
    public static void displayAvailableRooms(Date checkInDate, Date checkOutDate){
        Collection<IRoom> availableRooms = hotelResource.findAvailableRooms(checkInDate,checkOutDate);
        for(IRoom availableRoom : availableRooms){
            System.out.println(availableRoom);
        }
    }

    // check if there is any rooms available.
    public static boolean isRoomAvailable(Date checkInDate, Date checkOutDate){
        Collection<IRoom> availableRooms = hotelResource.findAvailableRooms(checkInDate,checkOutDate);
        if(availableRooms.isEmpty()){
            return false;
        }
        return true;
    }

    public static boolean isYesOrNoValid(String choice){

            if(!choice.equals("Y") && !choice.equals("N")){
                return false;
            }else{
                return true;
            }

    }

    // convert date type to localDateTime type
    // get it from https://www.baeldung.com/java-date-to-localdate-and-localdatetime
    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    // convert localDate type to date type
    // get it from https://www.baeldung.com/java-date-to-localdate-and-localdatetime
    public static Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    // convert localDateTime type to date type
    // get it from https://www.baeldung.com/java-date-to-localdate-and-localdatetime
    public static Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

    // check if check in date is before today's date.
    public static boolean isCheckInDateValid(Date checkIn){
        LocalDateTime checkInDate = convertToLocalDateTimeViaInstant(checkIn);
        LocalDateTime today = LocalDateTime.now();
        if(checkInDate.isAfter(today)){
            return true;
        }
        return false;
    }

    // check if check out date is before or equal to check in date
    public static boolean isCheckOutDateValid(Date checkIn,Date checkOut){
        LocalDateTime checkInDate = convertToLocalDateTimeViaInstant(checkIn);
        LocalDateTime checkOutDate = convertToLocalDateTimeViaInstant(checkOut);
        if(checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)){
            return false;
        }
        return true;
    }

    // plus 7 days to user input date
    public static Date plus7DaysToInputDate(Date date){
        LocalDateTime newDate = convertToLocalDateTimeViaInstant(date);
        return convertToDateViaSqlTimestamp(newDate.plusDays(7));
    }
}
