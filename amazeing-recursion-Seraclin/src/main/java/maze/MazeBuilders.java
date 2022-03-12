package maze;

public class MazeBuilders implements MazeBuilder {
    @Override
    public Room createRoom(String longDescription, String shortDescription) {
        if (longDescription == null || shortDescription == null) {
            throw new NullPointerException();
        }
        return new Rooms(longDescription, shortDescription, false);
    }

    @Override
    public Room createExit(String longDescription, String shortDescription) {
        if (longDescription == null || shortDescription == null) {
            throw new NullPointerException();
        }
        return new Rooms(longDescription, shortDescription, true);
    }

    @Override
    public MazeBuilder addPassage(Room room0, Room room1) {
        if (room0 == null || room1 == null) {
            throw new NullPointerException();
        }
        addOneWayPassage(room0, room1);
        addOneWayPassage(room1, room0);
        return this;
    }

    @Override
    public MazeBuilder addOneWayPassage(Room fromRoom, Room toRoom) {
        if (fromRoom == null || toRoom == null) {
            throw new NullPointerException();
        }
        if (fromRoom.getRooms().contains(toRoom) == -1) {
            fromRoom.getRooms().insertLast(toRoom);
        }
        return this;
    }
}
