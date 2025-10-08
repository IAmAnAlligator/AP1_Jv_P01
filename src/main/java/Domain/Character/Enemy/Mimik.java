package Domain.Character.Enemy;

import Domain.Character.Player.Player;
import Domain.Dungeon.Level;
import Domain.Dungeon.Room;
import Domain.Navigator.GetPosition;
import Domain.Navigator.PositionInterface;

public class Mimik extends Enemy {

  public Mimik(int health, int agility, int strength, boolean isAlive, int hostility,
      PositionInterface startPosition){
    super(health, agility, strength, isAlive, hostility, EnemyType.MIMIK, "Mimik",
        startPosition);
  }

  @Override
  public String onPlayerEncounter(Player player, Level level) {
    if (!isVisible()) {
      setVisible(true);
    }
    return super.onPlayerEncounter(player, level);
  }

  @Override
  public void enemyMove(GetPosition player, Room room) {
    if(isVisible()){
      standardMove(player, room);
    }
  }

  @Override
  public boolean shouldPursue(double distance) {
    return distance <= getHostility();
  }
}