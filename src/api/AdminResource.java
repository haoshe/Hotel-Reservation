package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    /**
     * the user can add more than one room and that is why the List<IRoom> is being passed as a parameter,
     * bearing in mind that here the ReservationService's addRoom method will be used to persist each one of
     * the rooms that were sent by the user.
     *
     * @param rooms
     */
    public void addRoom(List<IRoom> rooms){
        for(IRoom room : rooms){
            reservationService.addRoom(room);
        }
    }

    /**
     * getAllRoom method aims to return all the rooms that the hotel has and for this it will make use of the getAllRooms
     * method also present in the ReservationService class that also has the purpose to return all the rooms.
     *
     * @return
     */
    public Collection<IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }

    public void getAllCustomers(){
        System.out.println(customerService.getAllCustomers());
    }

    public void displayAllReservations(){
        reservationService.printAllReservation();
    }
}
