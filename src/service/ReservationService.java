package service;

import model.*;

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


    private static boolean isReserved(IRoom room, Date checkInDate, Date checkOutDate){
        // create a list for not available rooms
        // loop through each reservation in reservations list.
        // first check the dates, if the input dates fall between any reserved dates, or reserved dates are between the new input dates,
        // or the input checkout date falls between reserved dates, or input check in date falls between reserved dates
        // it means whichever room in the reservation list is not available for the input dates.
        // add that room to the not available rooms list.
        // then check each room in the rooms list, if a room from rooms list is in the not available list, means the room is reserved for the input dates, return true. Otherwise, return false.
        List<IRoom> notAvailableRooms = new ArrayList<>();
        for(Reservation reservation : reservations){

            if(checkInDate.compareTo(reservation.getCheckInDate())>=0 && checkOutDate.compareTo(reservation.getCheckOutDate())<=0 // fall between reserved dates
               || checkInDate.compareTo(reservation.getCheckInDate())<=0 && checkOutDate.compareTo(reservation.getCheckOutDate())>=0// input dates include reserved dates
                || checkInDate.compareTo(reservation.getCheckOutDate())<0 && checkOutDate.compareTo(reservation.getCheckOutDate())<=0// input checkout date falls between reserved dates
                || checkInDate.compareTo(reservation.getCheckInDate())>=0 && checkOutDate.compareTo(reservation.getCheckOutDate())>=0// input checkin date falls between reserved dates
            ){
                notAvailableRooms.add(reservation.getRoom());
            }
        }
        if(notAvailableRooms.contains(room)){
            return true;
        }
        return false;
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
