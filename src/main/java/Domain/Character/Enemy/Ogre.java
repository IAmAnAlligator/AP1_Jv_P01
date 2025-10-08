package Domain.Character.Enemy;

import Domain.Dungeon.Room;
import Domain.Navigator.Direction;
import Domain.Navigator.GetPosition;
import Domain.Navigator.PositionInterface;

public class Ogre extends Enemy {

  public Ogre(int health, int agility, int strength, boolean isAlive, int hostility,
      PositionInterface positionInterface) {
    super(health, agility, strength, isAlive, hostility, EnemyType.OGRE, "Ogre",
        positionInterface);
  }

  @Override
  public void enemyMove(GetPosition player, Room room) {
    if (getRestCounter() <= 0) {

      PositionInterface currentPos = getPosition();
      double distance = calculateDistance(player);

      if (shouldPursue(distance) && hasPathTo(player, room)) {
        Direction dir = getDirectionTo(player.getPosition());
        PositionInterface nextPos = currentPos.move(dir, 1);
        if (checkEnemyPosition(nextPos, player, room)) {
          setPosition(nextPos);
        }
      } else {
        Direction[] dir = Direction.values();
        Direction randDir = dir[random.nextInt(dir.length)];
        PositionInterface nextPos = currentPos.move(randDir, 2);
        if (checkEnemyPosition(nextPos, player, room)) {
          setPosition(nextPos);
        } else {
          nextPos = currentPos.move(randDir, 1);
          if (checkEnemyPosition(nextPos, player, room)){
            setPosition(nextPos);
          }
        }
      }


    } else {
      setRestCounter(getRestCounter() - 1);
    }

  }

  @Override
  public boolean shouldPursue(double distance) {
    return distance <= getHostility();
  }
}
