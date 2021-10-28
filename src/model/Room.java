package model;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber.equals(room.roomNumber) && price.equals(room.price) && enumeration == room.enumeration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, price, enumeration);
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + " " + enumeration + " Room Price: €" + price +"\n";
    }
}
