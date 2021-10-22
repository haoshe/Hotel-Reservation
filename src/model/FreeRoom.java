package model;

public class FreeRoom extends Room{


    public FreeRoom(String roomNumber, RoomType enumeration) {
        super(roomNumber,enumeration);
        this.price = 0.0;
    }

    public String toString(){
        return "This is a Free room";
    }

}
