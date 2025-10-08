package Presentation;

import Datalayer.Converter.GameConverter;
import Datalayer.DTO.GameDTO;
import Datalayer.DTO.ScoreDTO;
import Datalayer.DataLayer;
import Datalayer.ScoreDataLayer;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.KeyStroke;
import Domain.GameSession.GameFacadeInterface;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

  private final GameFacadeInterface game;
  private final Screen screen;
  private int currentLevel;
  private boolean gameExit = false;
  private static final Logger log = Logger.getLogger(Controller.class.getName());

  public Controller(Screen screen, GameFacadeInterface game) {
    this.screen = screen;
    this.game = game;
  }

  public void startGame() {

    game.startGame();

    StartScreen startScreen = new StartScreen(screen, game);
    Dungeon dungeon = new Dungeon(screen, game);
    Information information = new Information(screen, game);
    EndScreen endScreen = new EndScreen(screen, game);

    gameCycle(screen, startScreen, dungeon, information, endScreen);

  }

  public void startMenu(StartScreen startScreen) {
    startScreen.showStartScreen();

    if (!startScreen.isNewGame()) {
      loadGameFromJson();
    } else {
      if (game.getPlayer() != null) {
        game.getPlayer().resetStats(); // чистый старт
      }
    }

  }

  public void input(Screen screen) throws IOException {

    KeyStroke key = screen.readInput();
    if (key != null) {
      String command = null;

      if (key.getKeyType() == KeyType.Character) {
        switch (Character.toLowerCase(key.getCharacter())) {
          case 'w' -> command = "w";
          case 's' -> command = "s";
          case 'd' -> command = "d";
          case 'a' -> command = "a";
          case 'h' -> command = "h";
          case 'j' -> command = "j";
          case 'k' -> command = "k";
          case 'e' -> command = "e";
        }
      } else if (key.getKeyType() == KeyType.ArrowUp) {
        command = "w";
      } else if (key.getKeyType() == KeyType.ArrowDown) {
        command = "s";
      } else if (key.getKeyType() == KeyType.ArrowRight) {
        command = "d";
      } else if (key.getKeyType() == KeyType.ArrowLeft) {
        command = "a";
      } else if (key.getKeyType() == KeyType.Escape) {
        screen.stopScreen();
        gameExit = true;
      }

      if (command != null) {
        game.processCommand(command);
      }
    }

  }

  public void gameMatch(Screen screen, Dungeon dungeon, Information information)
      throws IOException {

    screen.clear();
    dungeon.showDungeon();
    information.showInfo();
    screen.refresh();

    input(screen);

    // проверяем смену уровня
    int newLevel = game.getLevel().getLevel();
    if (newLevel != currentLevel) {
      saveGameToJson();
      currentLevel = newLevel;
    }
  }

  public void gameCycle(Screen screen, StartScreen startScreen, Dungeon dungeon,
      Information information, EndScreen endScreen) {
    try {
      screen.startScreen(); // запуск один раз

      while (!gameExit && startScreen.isStartScreenExit()) {

        startMenu(startScreen);

        if (startScreen.isStartScreenExit()) {

          currentLevel = game.getLevel().getLevel();

          // основной цикл игры
          while (!gameExit && !dungeon.isFinished() && !game.isOver()) {
            gameMatch(screen, dungeon, information);
          }

          // Игра завершена — сохраняем результаты
          if (!gameExit) {
            savePlayerResult();
            endScreen.showScoreboard();
            dungeon.clean();
            game.startGame();
            screen.clear();
            screen.refresh();
          }

        }
      }

      screen.stopScreen();


    } catch (IOException e) {
      log.log(Level.SEVERE, "Error in game loop", e);
      throw new RuntimeException(e);
    }
  }


  private void loadGameFromJson() {
    GameDTO gameDTO = DataLayer.load();

    if (gameDTO == null) {
      log.info("Failed to load game data. Initializing a new game...");
    } else {

      GameFacadeInterface loadedGame = GameConverter.fromDTO(gameDTO);

      if (loadedGame == null) {
        log.info("Failed to convert GameDTO to Game. Initializing a new game...");
      } else {
        game.setLevel(loadedGame.getLevel());
        game.setPlayer(loadedGame.getPlayer());

        log.info("Game loaded successfully!");

      }
    }
  }

  private void saveGameToJson() {
    GameDTO gameDTO = GameConverter.toDTO(game);
    DataLayer.save(gameDTO);
    log.info("Game saved at level: " + game.getLevel().getLevel());
  }

  private void savePlayerResult() {
    String playerName = game.getPlayer().getDisplayName();
    int gold = game.getPlayer().getGold();
    int level = game.getLevel().getLevel();
    int elixirUse = game.getPlayer().getElixirUse();
    int foodEaten = game.getPlayer().getFoodEaten();
    int monsterKills = game.getPlayer().getMonsterKills();
    int cellMove = game.getPlayer().getCellMove();
    int playerHits = game.getPlayer().getPlayerHits();
    int playerMisses = game.getPlayer().getPlayerMisses();

    ScoreDTO scoreDTO = new ScoreDTO(playerName, gold, level, elixirUse, foodEaten,
        monsterKills, cellMove, playerHits, playerMisses);
    ScoreDataLayer.addScore(scoreDTO);

    log.info("Score saved");
  }

}
