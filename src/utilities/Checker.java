package utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class Checker {
    public static void main(String[] args) throws ParseException {
        LocalDate today = LocalDate.now();
        System.out.println(today);
        String checkIn = "10/23/2021";
        String checkOut = "34/21/2021";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");

        LocalDate start = LocalDate.parse(checkIn,formatter);
        start.isBefore(today);
       // System.out.println(start.getClass().getSimpleName());

        Date startDate = Utilities.convertToDateViaSqlDate(start);
        System.out.println(start.getClass().getSimpleName());
        System.out.println(startDate.getClass().getSimpleName());
//        try{
//           LocalDate start = LocalDate.parse(checkIn,formatter);
//        }catch (DateTimeParseException e){
//          //  System.out.println(e.getMessage());
//            System.out.println("invalid");
//        }
//        try {
//            LocalDate start = LocalDate.parse(checkIn, formatter);
//        }catch (Exception e){
//            System.out.println("invalid");
//        }
       // LocalDate finish = LocalDate.parse(checkOut,formatter);

//        if(finish.isBefore(start)){
//            System.out.println("unvalid");
//        }

//        if(start.isBefore(today)){
//            System.out.println("in the past");
//        }else{
//            System.out.println("in the future");
//        }
    }
}
