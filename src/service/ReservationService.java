package service;

import model.*;
import utilities.Utilities;

import java.time.LocalDateTime;
import java.util.*;

public class ReservationService {
    /*
     * The ReservationService is not only responsible for making the reservation, but also for
     * adding and listing the rooms.
     */

    // all rooms in the hotel
    public static Collection<IRoom> rooms = new HashSet<>();
    // already reserved rooms list
    private static Set<Reservation> reservations = new HashSet<>();

    private static Reservation reservedRoom;
    private static ReservationService reservationService;

    private ReservationService(){};

    public static ReservationService getInstance(){
        if(Objects.isNull(reservationService)){
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    /*
     * It is responsible for adding only one room in the collection that will contain all the rooms in the application, being this the reason
     * why the method receives IRoom instead of Reservation.
     *
     *
     */
    public boolean addRoom(IRoom room){
       rooms.add(room);
       return true;
    }

    /*
     * Returns a room from the collection rooms, giving the roomId that was passed.
     *
     *
     *
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

    public Collection<IRoom> findAvailableRooms(Date checkInDate, Date checkOutDate){
        // loop through all the rooms, if any room is not reserved, add to the available rooms list
        Set<IRoom> availableRooms = new HashSet<>();
        for(IRoom room : rooms){
            if(!isReserved(room,checkInDate,checkOutDate)){
                System.out.println(room.getRoomNumber() + "  is not reserved");
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }



    // check if the room is in the reserved rooms list judging by the input dates, return true if it is reserved for the input dates.
    // if reservation list is not empty, loop through each reservation in reservations list.
    // check if the room is in the reserved list
    // first check the dates, if the input check out date is before the reserved check in date,
    // or the input check in date is after the reserved check out date
    // it means the room is available for the input dates, even it is in the reservation list.
    private static boolean isReserved(IRoom room, Date checkInDate, Date checkOutDate){

        boolean isAvailable = false;
        if(!reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                IRoom reservedRoom = reservation.getRoom();
                if (reservedRoom.getRoomNumber().equals(room.getRoomNumber())) {

                    Date reservedCheckInDate = reservation.getCheckInDate();
                    Date reservedCheckOutDate = reservation.getCheckOutDate();

                    if (conflictsWithRange(checkInDate, checkOutDate, reservedCheckInDate, reservedCheckOutDate)) {
                        isAvailable = true;
                        return isAvailable;
                    }
                }
            }
        }
        return isAvailable;
    }

    private static boolean conflictsWithRange(Date checkInDate, Date checkOutDate,Date reservedCheckIn,Date reservedCheckOut){
        LocalDateTime reservedCheckInDate = Utilities.convertToLocalDateTimeViaInstant(reservedCheckIn);
        LocalDateTime reservedCheckOutDate = Utilities.convertToLocalDateTimeViaInstant(reservedCheckOut);
        LocalDateTime checkIn = Utilities.convertToLocalDateTimeViaInstant(checkInDate);
        LocalDateTime checkOut = Utilities.convertToLocalDateTimeViaInstant(checkOutDate);
        if (checkOut.isBefore(reservedCheckInDate) || checkIn.isAfter(reservedCheckOutDate)) {
            return false;// does not conflict
        }
        return true;
    }

    public List<Reservation> getCustomerReservation(String email){
      List<Reservation> customerReservations = new ArrayList<>();
        for(Reservation reservation : reservations){
            if(reservation.getCustomer().getEmail().equals(email)){
                customerReservations.add(reservation);
            }
        }
         return customerReservations;
    }

    public static Collection<IRoom> getAllRooms(){
        return rooms;
    }


    public Set<Reservation> getAllReservations(){
        return reservations;
    }

}
