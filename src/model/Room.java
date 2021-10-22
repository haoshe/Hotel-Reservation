package model;

public class Room implements IRoom{

    protected String roomNumber;
    protected Double price;
    protected RoomType enumeration;

    public Room(String roomNumber, RoomType enumeration){
        this.roomNumber = roomNumber;
        this.enumeration = enumeration;
    }

    public Room(String roomNumber, Double price, RoomType enumeration) {
        super();
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public Boolean isFree() {
        return null;
    }


    @Override
    public String toString() {
        return "Room Number: " + roomNumber + " " + enumeration + " Room Price: €" + price +"\n";
    }
}
