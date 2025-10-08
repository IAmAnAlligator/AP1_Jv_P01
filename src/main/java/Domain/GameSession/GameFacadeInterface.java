package Domain.GameSession;

import Domain.Character.Player.Player;
import Domain.Dungeon.Level;

public interface GameFacadeInterface {

  void startGame();

  void processCommand(String command);

  void nextLevel();

  boolean isOver();

  Player getPlayer();

  Level getLevel();

  String getCombatInfo();

  void setPlayer(Player player);

  void setLevel(Level level);

}
