package Domain.Dungeon;

import Domain.Character.Enemy.Enemy;
import Domain.Character.Player.Player;
import Domain.GameSession.Game;
import Domain.GameSession.GameSettings;
import Domain.Item.ItemInterface;
import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;
import Domain.Navigator.ValidateLocation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class Room implements RoomInterface, ValidateLocation {

  private final PositionInterface position;
  private final int width, height;
  private List<ItemInterface> items;
  private List<Enemy> enemies;
  private List<PositionInterface> walls;
  private List<PositionInterface> doors;
  private boolean isStart;
  private boolean isEnd;
  private static final Random random = new Random();

  public Room(PositionInterface position, int width, int height) {
    this.position = position;
    this.width = width;
    this.height = height;
    this.items = new ArrayList<>();
    this.enemies = new ArrayList<>();
    this.walls = listWalls();
    this.isStart = false;
    this.isEnd = false;
    this.doors = new ArrayList<>();
  }

  public void addDoorIfCorridorConnects(Corridor corridor) {
    for (PositionInterface pos : corridor.getPath()) {
      for (PositionInterface wall : walls) {
        if (pos.getX() == wall.getX() && pos.getY() == wall.getY()) {
          if (!doors.contains(wall)) {
            doors.add(wall);
          }
        }
      }
    }
  }

  @Override
  public void addItemToLocation(ItemInterface item, Player player) {
    PositionInterface newPos = new Position(
        player.getPosition().getX() + 1,
        player.getPosition().getY() + 1
    );
    item.setPosition(newPos);
    addItem(item);
  }

  @Override
  public void moveEnemies(Player player) {
    for (Enemy enemy : enemies) {
      if (enemy.isAlive()) {
        enemy.enemyMove(player, this);
      }
    }
  }

  @Override
  public void onPlayerEnter(Player player, PositionInterface endPos, Game game) {
    // Враги
    Iterator<Enemy> iterator = enemies.iterator();
    while (iterator.hasNext()) {
      Enemy enemy = iterator.next();
      if (!enemy.isAlive()) {
        iterator.remove();
        player.setMonsterKills(player.getMonsterKills() + 1);
      } else if (enemy.getPosition().equals(endPos)) {
        String combatLog = enemy.onPlayerEncounter(player, game.getLevel());
        game.setLastCombatLog(combatLog);


      }
    }

    // Предметы
    items.removeIf(item -> item.onPickup(player, endPos));

    player.updateElixirEffect();

    // Новый уровень
    if (endPos.equals(game.getLevel().getExitPosition())) {
      game.newLevel();
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }


  @Override
  public List<PositionInterface> getDoors() {
    return doors;
  }

  @Override
  public void setDoors(List<PositionInterface> doors) {
    this.doors.addAll(doors);
  }


  @Override
  public PositionInterface getPosition() {
    return position;
  }

  @Override
  public List<ItemInterface> getItems() {
    return items;
  }

  @Override
  public void addItem(ItemInterface item) {
    items.add(item);
  }

  @Override
  public List<Enemy> getEnemies() {
    return enemies;
  }

  @Override
  public void addEnemy(Enemy enemy) {
    enemies.add(enemy);
  }


  @Override
  public List<PositionInterface> getWalls() {
    return walls;
  }


  @Override
  public boolean isValid(PositionInterface pos) {
    int x = pos.getX();
    int y = pos.getY();

    // Проверка, что позиция находится внутри границ комнаты
    boolean withinBounds = x > position.getX() && x < position.getX() + width - 1 &&
        y > position.getY() && y < position.getY() + height - 1;

    // Проверка, что позиция не является частью стены
    //boolean notWall = !walls.contains(pos);

    // Проверка, является ли позиция дверью
    boolean isDoor = doors.contains(pos);

    // Возвращаем true, если позиция внутри комнаты и не является стеной
    // или если это дверь (дверь всегда валидная позиция)
    return withinBounds || isDoor;
  }


  @Override
  public boolean isCross(List<Room> rooms) {
    for (Room other : rooms) {
      if (other != this) {

        int thisLeft = position.getX() - GameSettings.MIN_ROOM_DISTANCE;
        int thisRight = position.getX() + width + GameSettings.MIN_ROOM_DISTANCE;
        int thisTop = position.getY() - GameSettings.MIN_ROOM_DISTANCE;
        int thisBottom = position.getY() + height + GameSettings.MIN_ROOM_DISTANCE;

        int otherLeft = other.position.getX();
        int otherRight = other.position.getX() + other.width;
        int otherTop = other.position.getY();
        int otherBottom = other.position.getY() + other.height;

        if (thisLeft < otherRight &&
            thisRight > otherLeft &&
            thisTop < otherBottom &&
            thisBottom > otherTop) {
          return true;
        }
      }
    }
    return false;
  }


  public boolean isParallel(List<Room> rooms) {
    int thisLeft = this.getPosition().getX();
    int thisRight = thisLeft + this.getWidth() - 1;
    int thisTop = this.getPosition().getY();
    int thisBottom = thisTop + this.getHeight() - 1;

    for (Room other : rooms) {
      if (other != this) {

        int otherLeft = other.getPosition().getX();
        int otherRight = otherLeft + other.getWidth() - 1;
        int otherTop = other.getPosition().getY();
        int otherBottom = otherTop + other.getHeight() - 1;

        // Вертикальная параллельность (левая или правая граница совпадает)
        if (thisLeft == otherLeft || thisRight == otherRight) {
          return true;
        }

        // Горизонтальная параллельность (верхняя или нижняя граница совпадает)
        if (thisTop == otherTop || thisBottom == otherBottom) {
          return true;
        }
      }

    }

    return false;
  }


  @Override
  public Position getCenter() {
    int centerX = position.getX() + (width - 1) / 2;
    int centerY = position.getY() + (height - 1) / 2;
    return new Position(centerX, centerY);
  }


  public boolean isStart() {
    return isStart;
  }

  public void setStart(boolean start) {
    isStart = start;
  }

  public boolean isEnd() {
    return isEnd;
  }

  public void setEnd(boolean end) {
    isEnd = end;
  }


  public List<PositionInterface> listWalls() {
    List<PositionInterface> walls = new ArrayList<>();

    // Верхняя и нижняя границы
    for (int i = 0; i < width; i++) {
      walls.add(new Position(position.getX() + i, position.getY()));             // Верхняя
      walls.add(new Position(position.getX() + i, position.getY() + height - 1)); // Нижняя
    }

    // Левая и правая границы
    for (int j = 1; j < height - 1; j++) {
      walls.add(new Position(position.getX(), position.getY() + j));             // Левая
      walls.add(new Position(position.getX() + width - 1, position.getY() + j)); // Правая
    }

    return walls;
  }


  public Position getRandomPoint() {
    int randomX = random.nextInt(width - 2) + 1;
    int randomY = random.nextInt(height - 2) + 1;
    return new Position(position.getX() + randomX, position.getY() + randomY);
  }


  public List<PositionInterface> getFloor() {
    List<PositionInterface> floor = new ArrayList<>();
    int minX = position.getX();
    int minY = position.getY();
    int maxX = minX + width - 1;
    int maxY = minY + height - 1;

    Set<PositionInterface> wallSet = new HashSet<>(walls); // Быстрое contains()

    for (int y = minY + 1; y < maxY; y++) {
      for (int x = minX + 1; x < maxX; x++) {
        Position pos = new Position(x, y);
        if (!wallSet.contains(pos)) {
          floor.add(pos);
        }
      }
    }
    return floor;
  }


  public void setItems(List<ItemInterface> items) {
    this.items = items;
  }

  public void setEnemies(List<Enemy> enemies) {
    this.enemies = enemies;
  }

  public void setWalls(List<PositionInterface> walls) {
    this.walls = walls;
  }

  public boolean containsPlayer(Player player) {
    PositionInterface playerPos = player.getPosition();
    return getFloor().stream()
        .anyMatch(floorPos -> floorPos.getX() == playerPos.getX()
            && floorPos.getY() == playerPos.getY());
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true; // проверка на ту же ссылку
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Room room = (Room) o;
    return width == room.width &&
        height == room.height &&
        isStart == room.isStart &&
        isEnd == room.isEnd &&
        Objects.equals(position,
            room.position); // предполагается, что Position корректно переопределяет equals()
  }

  @Override
  public int hashCode() {
    return Objects.hash(position, width, height, isStart, isEnd);
  }

}
