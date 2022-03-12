package maze;

import structure.ListInterface;
import structure.RecursiveLinkedList;

public class MazeSolver implements MazeSolution {
    private Maze maze;
    private ListInterface<Room> path;
    private ListInterface<Room> visited;

    public MazeSolver(Maze m) {
        path = new RecursiveLinkedList<>();
        visited = new RecursiveLinkedList<>();
        maze = m;
    }
    @Override
    public Maze getMaze() {
        return maze;
    }

    @Override
    public ListInterface<Room> getSolution() {
        if (!canBeSolved(maze.getStart())) {
            throw new UnsolvableMazeException();
        }
        return path;
    }
    public boolean canBeSolved(Room r) { // checks if the maze is solvable or not (no exit, infinite loop, etc.)
        if (r.isExit()) {
            path.insertFirst(r);
            return true;
        }
        visited.insertLast(r);
        ListInterface<Room> adjRooms = r.getRooms();
        for (int i = 0; i < r.getRooms().size(); i++) {
            if (visited.contains(adjRooms.get(i)) == -1 && canBeSolved(adjRooms.get(i))) {
                path.insertFirst(r);
                return true;
            }
        }
        return false;
    }

}
