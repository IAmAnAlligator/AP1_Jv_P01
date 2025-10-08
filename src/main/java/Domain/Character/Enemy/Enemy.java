package Domain.Character.Enemy;

import Domain.Character.Character;
import Domain.Character.Player.Player;
import Domain.Dungeon.Level;
import Domain.Dungeon.Room;
import Domain.Fight.Combat;
import Domain.Navigator.Direction;
import Domain.Navigator.GetPosition;
import Domain.Navigator.PositionInterface;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public abstract class Enemy extends Character implements EnemyMove {

  private final int hostility;
  private final String displayName;
  private final EnemyType type;
  private boolean isFirstHit = true;
  private int restCounter = 0;
  private boolean isVisible = true;
  protected Random random = new Random();

  public Enemy(int health, int agility, int strength, boolean isAlive, int hostility,
      EnemyType type, String displayName,
      PositionInterface startPosition) {
    super(health, agility, strength, isAlive, startPosition, displayName);
    this.hostility = hostility;
    this.type = type;
    this.displayName = displayName;
  }

  public String onPlayerEncounter(Player player, Level level) {
    Combat combat = new Combat(level);
    combat.attack(player, this);
    String combatInfo = combat.getCombatInfo();
    combat.cleanCombatInfo();
    return combatInfo;
  }

  public boolean checkEnemyPosition(PositionInterface position,
      GetPosition player, Room room) {
    return room.isValid(position) && !position.equals(player.getPosition());
  }

  public String getDisplayName() {
    return displayName;
  }

  public int getHostility() {
    return hostility;
  }

  public EnemyType getType() {
    return type;
  }

  public boolean isFirstHit() {
    return isFirstHit;
  }

  public int getRestCounter() {
    return restCounter;
  }

  public boolean isVisible() {
    return isVisible;
  }

  public void setFirstHit(boolean firstHit) {
    isFirstHit = firstHit;
  }

  public void setRestCounter(int restCounter) {
    this.restCounter = restCounter;
  }

  public void setVisible(boolean visible) {
    isVisible = visible;
  }

  public boolean hasPathTo(GetPosition player, Room room) {
    PositionInterface start = getPosition();
    Set<PositionInterface> visited = new HashSet<>();
    Queue<PositionInterface> queue = new LinkedList<>();
    queue.add(start);
    visited.add(start);

    while (!queue.isEmpty()) {
      PositionInterface current = queue.poll();
      if (current.equals(player.getPosition())) {
        return true;
      }

      for (Direction direction : Direction.values()) {
        PositionInterface next = current.move(direction, 1);
        if (room.isValid(next) && !visited.contains(next)) {
          queue.add(next);
          visited.add(next);
        }
      }
    }
    return false;
  }

  protected Direction getDirectionTo(PositionInterface target) {
    int dx = target.getX() - getPosition().getX();
    int dy = target.getY() - getPosition().getY();
    if (Math.abs(dx) > Math.abs(dy)) {
      return dx > 0 ? Direction.RIGHT : Direction.LEFT;
    } else {
      return dy > 0 ? Direction.DOWN : Direction.UP;
    }
  }

  public double calculateDistance(GetPosition target) {
    PositionInterface currentPos = getPosition();
    PositionInterface playerPos = target.getPosition();
    double firstExpr = Math.pow(Math.abs(currentPos.getX() - playerPos.getX()), 2);
    double secondDExpr = Math.pow(Math.abs(currentPos.getY() - playerPos.getY()), 2);
    return Math.sqrt(firstExpr + secondDExpr);
  }

  public void standardMove(GetPosition player, Room room) {
    PositionInterface currentPos = getPosition();
    double distance = calculateDistance(player);

    if (shouldPursue(distance) && hasPathTo(player, room)) {
      // Преследуем игрока
      Direction dir = getDirectionTo(player.getPosition());
      PositionInterface nextPos = currentPos.move(dir, 1);
      tryMove(room, nextPos, player.getPosition());
    } else {
      // Двигаемся случайно
      Direction[] directions = Direction.values();
      Direction randDir = directions[random.nextInt(directions.length)];
      PositionInterface nextPos = currentPos.move(randDir, 1);
      tryMove(room, nextPos, player.getPosition());
    }
  }

  private void tryMove(Room room, PositionInterface nextPos, PositionInterface playerPos) {
    // Не заходим в клетку игрока и не идём в двери
    if (!nextPos.equals(playerPos) &&
        room.isValid(nextPos) &&
        !room.getDoors().contains(nextPos)) {
      setPosition(nextPos);
    }
  }


}
