package maze;

import structure.ListInterface;
import structure.RecursiveLinkedList;

public class Rooms implements Room {
    private String longDesc;
    private String shortDesc;
    private boolean exit;
    ListInterface<Room> adjRooms;
    public Rooms (String l1, String s1, boolean e1) {
        longDesc = l1;
        shortDesc = s1;
        exit = e1;
        adjRooms = new RecursiveLinkedList<>();
    }
    @Override
    public String getFullDescription() {
        return longDesc;
    }

    @Override
    public String getShortDescription() {
        return shortDesc;
    }

    @Override
    public boolean isExit() {
        return exit;
    }

    @Override
    public ListInterface<Room> getRooms() {
        return adjRooms;
    }
}
