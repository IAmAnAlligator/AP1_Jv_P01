package Domain.Character.Enemy;

import Domain.Dungeon.Room;
import Domain.Navigator.GetPosition;

public interface EnemyMove {

  void enemyMove(GetPosition player, Room room);
  boolean shouldPursue(double distance);

}
