package model;

import utilities.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.*;

public class Tester {
    private static List<Reservation> reservations = new ArrayList<>();
    private static List<IRoom> rooms = new ArrayList<>();
    private static List<IRoom> availableRooms = new ArrayList<>();
    private static final DateTimeFormatter dateFormat = new DateTimeFormatterBuilder()
            .parseStrict()
            .appendPattern("MM/dd/uuuu")
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);
    public static void main(String[] args) throws ParseException {

        Customer customer1 = new Customer("hao@gmail.com", "Hao","She");
        Customer customer2 = new Customer("qi@gmail.com","qi","jiang");
        IRoom room1 = new Room("101",129.00, RoomType.SINGLE);
        IRoom room2 = new Room("102",149.00,RoomType.DOUBLE);
        IRoom room3 = new Room("103",149.00,RoomType.DOUBLE);

        Date checkInDate1 = new SimpleDateFormat("MM/dd/yyyy").parse("10/28/2021");
        Date checkOutDate1 = new SimpleDateFormat("MM/dd/yyyy").parse("10/29/2021");

        Date checkInDate2 = new SimpleDateFormat("MM/dd/yyyy").parse("11/28/2021");
        Date checkOutDate2 = new SimpleDateFormat("MM/dd/yyyy").parse("11/29/2021");

        Date checkInDate3 = new SimpleDateFormat("MM/dd/yyyy").parse("12/28/2021");
        Date checkOutDate3 = new SimpleDateFormat("MM/dd/yyyy").parse("12/29/2021");

        Date checkInDate4 = new SimpleDateFormat("MM/dd/yyyy").parse("10/28/2021");
        Date checkOutDate4 = new SimpleDateFormat("MM/dd/yyyy").parse("10/29/2021");
//        System.out.println(customer);

        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);

        Reservation reservation1 = new Reservation(customer1,room1,checkInDate1,checkOutDate1);
        Reservation reservation2 = new Reservation(customer2,room2,checkInDate2,checkOutDate2);
        Reservation reservation3 = new Reservation(customer2,room2,checkInDate3,checkOutDate3);
        Reservation reservation4 = new Reservation(customer1,room1,checkInDate3,checkOutDate3);


        reservations.add(reservation1);
        reservations.add(reservation2);
        reservations.add(reservation3);
        reservations.add(reservation4);

        for(Reservation reservation : reservations){
            System.out.println(reservation);
        }

        System.out.println("***************************************************************");
//        for(IRoom room : rooms){
//
//            for (Reservation r : reservations){
//                if(r.getRoom().equals(room)){
//                    System.out.println(r.getCheckInDate());
//                }
//        }

//        for(IRoom room : rooms){
//            if(!isReserved(room, checkInDate3,checkOutDate3)){
//                //System.out.println(" is not reserved.");
//                availableRooms.add(room);
//                break;
//            }else{
//                System.out.println("is not reserved");
//            }
//        }
//        for(IRoom r : availableRooms){
//            System.out.println(r);
//        }

        Collection<IRoom> availableRooms = findRooms(checkInDate2,checkOutDate2);
        for(IRoom room : availableRooms){
            System.out.println(room);
        }



    }

    public static boolean isReserved(IRoom r, Date checkInInput, Date checkOutInput){
        // if reservation list is not empty, loop through each reservation in reservations list.
        // check if the room is in the reserved list
        // first check the dates, if the input check out date is before the reserved check in date,
        // or the input check in date is after the reserved check out date
        // it means the room in the reservation list is available for the input dates.
        if(!reservations.isEmpty()){
            for(Reservation reservation : reservations) {
                if(reservation.getRoom().equals(r)){
                    LocalDateTime resevredCheckInDate = Utilities.convertToLocalDateTimeViaInstant(reservation.getCheckInDate());
                    LocalDateTime reservedCheckOutDate = Utilities.convertToLocalDateTimeViaInstant(reservation.getCheckOutDate());
                    LocalDateTime checkIn = Utilities.convertToLocalDateTimeViaInstant(checkInInput);
                    LocalDateTime checkOut = Utilities.convertToLocalDateTimeViaInstant(checkOutInput);
                    if (checkOut.isBefore(resevredCheckInDate) || checkIn.isAfter(reservedCheckOutDate)) {
                        return false;//is not reserved
                    }
                    return true; // is reserved
                }else{
                    return false;
                }
            }
        }

        return false;// reservation list is empty, so is not reserved
    }

    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        // loop through all the rooms, if any room is not reserved, add to the available rooms list
        for(IRoom room : rooms){
            if(!isReserved(room,checkInDate,checkOutDate)){
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
}
