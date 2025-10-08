package Domain.GameSession;

import Domain.Character.Player.Bag;
import Domain.Character.Player.Player;
import Domain.Dungeon.Level;
import Domain.Dungeon.Room;
import Domain.Item.ItemInterface;
import Domain.Navigator.Direction;
import Domain.Navigator.Position;
import Domain.Navigator.PositionInterface;
import Domain.Navigator.ValidateLocation;
import java.util.Iterator;

public class Game {

  private Level level;
  private Player player;
  private boolean gameOver = false;
  private String lastCombatLog;

  Game() {
  }

  public void setLastCombatLog(String log) {
    this.lastCombatLog = log;
  }

  public String getLastCombatLog() {
    return lastCombatLog;
  }

  void initializeGame() {
    PositionInterface position = new Position();
    Bag bag = new Bag();
    level = new Level();
    player = new Player(GameSettings.MAX_HEALTH, GameSettings.INITIAL_AGILITY,
        GameSettings.INITIAL_STRENGTH, true, position,
        bag);
    generateDungeon();
    setStartPlayerPosition();
    level.setLevel(1);
    gameOver = false;
    clearCombatLog();

  }

  private void generateDungeon() {
    level.setRooms(level.generateRooms());
    level.setCorridors(level.generateCorridors());
  }

  private void setStartPlayerPosition() {
    for (Room room : level.getRooms()) {
      if (room.isStart()) {
        player.setPosition(room.getCenter());
      }
    }
  }

  public Player getPlayer() {
    return player;
  }

  void processTurn(String command) {
    ValidateLocation currentLocation = level.getPositionAt(player.getPosition());

    switch (command) {
      case "w" -> movePlayer(Direction.UP, currentLocation);
      case "a" -> movePlayer(Direction.LEFT, currentLocation);
      case "d" -> movePlayer(Direction.RIGHT, currentLocation);
      case "s" -> movePlayer(Direction.DOWN, currentLocation);
      case "h" -> useWeapon(); // оружие
      case "j" -> useFood();// еда
      case "k" -> useElixir(); // эликсир
      case "e" -> useScroll(); // свиток
      case "q" -> gameOver = true;
    }

    moveEnemies(player, currentLocation);
  }


  private void moveEnemies(Player player, ValidateLocation location) {
    if (player != null && location != null) {
      location.moveEnemies(player);
    }
  }

  private void movePlayer(Direction direction, ValidateLocation location) {
    PositionInterface currentPos = player.getPosition();
    PositionInterface newPos = currentPos.move(direction, 1);

    ValidateLocation nextLocation = level.getPositionAt(newPos);

    if (nextLocation != null) {
      player.playerMove(direction, nextLocation);

      PositionInterface endPos = player.getPosition();

      // Любая локация сама обрабатывает вход игрока
      location.onPlayerEnter(player, endPos, this);

      if (!player.isAlive()) {
        gameOver = true;
      }
    }

  }


  public void newLevel() {
    level.setLevel(level.getLevel() + 1);
    isLastLevel();
    generateDungeon();
    setStartPlayerPosition();
  }

  private void isLastLevel() {
    if (level.getLevel() == 22) {
      level.setLastLevel(true);
      gameOver = true;
      level.setLevel(21);
    }
  }


  private void useWeapon() {
    Iterator<ItemInterface> it = player.getBag().getItems().iterator();
    while (it.hasNext()) {
      ItemInterface item = it.next();
      if (item.isWeapon()) {
        item.useItem(player);
        it.remove(); // безопасное удаление
        break;
      }
    }
  }

  private void useFood() {
    Iterator<ItemInterface> it = player.getBag().getItems().iterator();
    while (it.hasNext()) {
      ItemInterface item = it.next();
      if (item.isFood()) {
        item.useItem(player);
        it.remove();
        break;
      }
    }
  }

  private void useElixir() {
    Iterator<ItemInterface> it = player.getBag().getItems().iterator();
    while (it.hasNext()) {
      ItemInterface item = it.next();
      if (item.isElixir()) {
        item.useItem(player);
        it.remove();
        break;
      }
    }
  }

  private void useScroll() {
    Iterator<ItemInterface> it = player.getBag().getItems().iterator();
    while (it.hasNext()) {
      ItemInterface item = it.next();
      if (item.isScroll()) {
        item.useItem(player);
        it.remove();
        break;
      }
    }
  }


  public Level getLevel() {
    return level;
  }

  void setPlayer(Player player) {
    this.player = player;
  }

  void setLevel(Level level) {
    this.level = level;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public void clearCombatLog() {
    setLastCombatLog(null);
  }


}