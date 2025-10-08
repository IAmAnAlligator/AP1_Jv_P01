package Domain.Dungeon;

import Domain.Navigator.PositionInterface;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class GenerateCorridor {

  public static List<Corridor> generateCorridors(List<Room> rooms) {
    List<Corridor> corridors = new ArrayList<>();
    Set<Room> connectedRooms = new HashSet<>();
    PriorityQueue<Edge> edgesQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.distance));

    Room startRoom = rooms.stream().filter(Room::isStart).findFirst()
        .orElse(rooms.isEmpty() ? null : rooms.get(0));
    if (startRoom == null) return corridors;

    connectedRooms.add(startRoom);
    addEdgesFrom(startRoom, rooms, connectedRooms, edgesQueue);

    while (connectedRooms.size() < rooms.size() && !edgesQueue.isEmpty()) {
      Edge edge = edgesQueue.poll();

      Room newRoom = null;
      if (connectedRooms.contains(edge.room1) && !connectedRooms.contains(edge.room2)) {
        newRoom = edge.room2;
      } else if (connectedRooms.contains(edge.room2) && !connectedRooms.contains(edge.room1)) {
        newRoom = edge.room1;
      }

      if (newRoom != null) {
        corridors.add(new Corridor(edge.room1.getCenter(), edge.room2.getCenter(), rooms));
        connectedRooms.add(newRoom);
        addEdgesFrom(newRoom, rooms, connectedRooms, edgesQueue);
      }
    }

    return corridors;
  }

  private static void addEdgesFrom(Room from, List<Room> rooms, Set<Room> connected, PriorityQueue<Edge> queue) {
    for (Room other : rooms) {
      if (!connected.contains(other)) {
        int distance = getManhattanDistance(from.getCenter(), other.getCenter());
        queue.add(new Edge(from, other, distance));
      }
    }
  }

  private static int getManhattanDistance(PositionInterface p1, PositionInterface p2) {
    return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
  }

  // Класс для хранения 2 комнат и расстояния между ними
  private static class Edge {
    Room room1, room2;
    int distance;

    Edge(Room room1, Room room2, int distance) {
      this.room1 = room1;
      this.room2 = room2;
      this.distance = distance;
    }
  }

}
