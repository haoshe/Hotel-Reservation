package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Objects;
import java.util.Date;
import java.util.Set;

public class HotelResource {

    private static HotelResource hotelResource;
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();


    private HotelResource(){}

    public static HotelResource getInstance(){
        if(Objects.isNull(hotelResource)){
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName){
        customerService.addCustomer(email,firstName,lastName);
    }

    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }


    public Reservation bookARoom(String customerEmail, IRoom room, Date checkedInDate, Date checkedOutDate){
        Customer customer = HotelResource.hotelResource.getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room,checkedInDate,checkedOutDate);
    }

    public Collection<Reservation> getCustomerReservations(String customerEmail){
        Customer customer = hotelResource.getCustomer(customerEmail);
        return reservationService.getCustomerReservation(customer);
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        return reservationService.findRooms(checkIn,checkOut);
    }

    public Set<IRoom> getAvailableRooms(){
        return reservationService.findAvailableRooms();
    }
}
