package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AdminResource {
    private static AdminResource adminResource;
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();


    private AdminResource(){

    }
    public static AdminResource getInstance(){
        if(Objects.isNull(adminResource)){
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }


    public boolean addRoom(IRoom room){
            return reservationService.addRoom(room);
    }

    /*
     * getAllRoom method aims to return all the rooms that the hotel has and for this it will make use of the getAllRooms
     * method also present in the ReservationService class that also has the purpose to return all the rooms.
     *
     *
     */
    public Collection<IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }

    public static Collection<Customer> getAllCustomers(){
       // System.out.println(customerService.getAllCustomers());
        return customerService.getAllCustomers();
    }

    public Set<Reservation> getAllReservations(){
        return reservationService.getAllReservations();
    }
}
