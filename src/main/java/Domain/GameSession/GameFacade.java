package Domain.GameSession;

import Domain.Character.Player.Player;
import Domain.Dungeon.Level;

public class GameFacade implements GameFacadeInterface {

  private final Game game;

  public GameFacade() {
    this.game = new Game();
  }

  @Override
  public void startGame() {
    game.initializeGame();
  }

  @Override
  public void processCommand(String command) {
    game.processTurn(command);
  }

  @Override
  public void nextLevel() {
    game.newLevel();
  }

  @Override
  public boolean isOver() {
    return game.isGameOver();
  }

  @Override
  public Player getPlayer() {
    return game.getPlayer();
  }

  @Override
  public Level getLevel() {
    return game.getLevel();
  }

  @Override
  public String getCombatInfo() {
    return game.getLastCombatLog();
  }

  @Override
  public void setPlayer(Player player) {
    game.setPlayer(player);
  }

  @Override
  public void setLevel(Level level) {
    game.setLevel(level);
  }

}
