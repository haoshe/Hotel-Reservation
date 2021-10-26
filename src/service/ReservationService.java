package service;

import model.*;
import utilities.Utilities;

import java.time.LocalDateTime;
import java.util.*;

public class ReservationService {
    /**
     * The ReservationService is not only responsible for making the reservation, but also for
     * adding and listing the rooms.
     */

    // all rooms in the hotel
    public static Collection<IRoom> rooms = new HashSet<>();

    // a list of available rooms according to user's input check in and checkout dates
    public static Set<IRoom> availableRooms = new HashSet<>();

    private static Set<Reservation> reservations = new HashSet<>();// already reserved rooms list
    private static Reservation reservedRoom;
    private static ReservationService reservationService;

    private ReservationService(){};

    public static ReservationService getInstance(){
        if(Objects.isNull(reservationService)){
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    /**
     * It is responsible for adding only one room in the collection that will contain all the rooms in the application, being this the reason
     * why the method receives IRoom instead of Reservation.
     *
     * @param room
     */
    public boolean addRoom(IRoom room){
       rooms.add(room);
       return true;
    }

    /**
     * Returns a room from the collection rooms, giving the roomId that was passed.
     *
     * @param roomId
     * @return
     */
    public IRoom getARoom(String roomId){
        for(IRoom room :rooms){
            if(roomId.equals(room.getRoomNumber())){
                return room;
            }
        }
        System.out.println("There is no room registered");
        return null;
    }

    // reserve a room and add the room to the reservations list
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
          reservedRoom = new Reservation(customer,room,checkInDate,checkOutDate);
          reservations.add(reservedRoom);
          return reservedRoom;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        // loop through all the rooms, if any room is not reserved, add to the available rooms list
        for(IRoom room : rooms){
            if(!isReserved(room,checkInDate,checkOutDate)){
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Set<IRoom> findAvailableRooms(){
        return availableRooms;
    }

    // check if the room is in the reserved rooms list judging by the input dates, return true if it is reserved for the input dates.
    private static boolean isReserved(IRoom room, Date checkInDate, Date checkOutDate){
        // if reservation list is not empty, loop through each reservation in reservations list.
        // check if the room is in the reserved list
        // first check the dates, if the input check out date is before the reserved check in date,
        // or the input check in date is after the reserved check out date
        // it means the room in the reservation list is available for the input dates.
        if(!reservations.isEmpty()){
            for(Reservation reservation : reservations) {
                if(reservation.getRoom().equals(room)){
                    LocalDateTime resevredCheckInDate = Utilities.convertToLocalDateTimeViaInstant(reservation.getCheckInDate());
                    LocalDateTime reservedCheckOutDate = Utilities.convertToLocalDateTimeViaInstant(reservation.getCheckOutDate());
                    LocalDateTime checkIn = Utilities.convertToLocalDateTimeViaInstant(checkInDate);
                    LocalDateTime checkOut = Utilities.convertToLocalDateTimeViaInstant(checkOutDate);
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

    public Reservation getCustomerReservation(String email){
        // CustomerService.getInstance().getCustomer(customer.getEmail());
        for(Reservation reservation : reservations){
            if(reservation.getCustomer().getEmail().equals(email)){
                return reservation;
            }
        }
         return null;
    }

    public static Collection<IRoom> getAllRooms(){
        return rooms;
    }

    public void printAllReservation(){
        for(Reservation reservation : reservations){
            System.out.println(reservation);
        }
    }
}
