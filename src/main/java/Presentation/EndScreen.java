package Presentation;

import Datalayer.ScoreDataLayer;
import Datalayer.DTO.ScoreDTO;
import Domain.GameSession.GameFacadeInterface;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.graphics.TextGraphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EndScreen {

  private final Screen screen;
  private final GameFacadeInterface game;

  public EndScreen(Screen screen, GameFacadeInterface game) {
    this.screen = screen;
    this.game = game;
  }

  public void showScoreboard() throws IOException {
    List<ScoreDTO> scores = ScoreDataLayer.loadScores();

    // Оставляем для каждого игрока только запись с максимальным количеством золота
    Map<String, ScoreDTO> bestScores = new HashMap<>();
    for (ScoreDTO s : scores) {
      bestScores.merge(s.getPlayerName(), s,
          (oldVal, newVal) -> newVal.getGold() > oldVal.getGold() ? newVal : oldVal);
    }

    // Преобразуем значения в список и сортируем по золоту по убыванию
    List<ScoreDTO> uniqueScores = new ArrayList<>(bestScores.values());
    uniqueScores.sort(Comparator.comparingInt(ScoreDTO::getGold).reversed());

    screen.clear();
    TextGraphics tg = screen.newTextGraphics();

    tg.putString(2, 1, "=== SCOREBOARD ===");

    int y = 3;
    for (int i = 0; i < Math.min(10, uniqueScores.size()); i++) {
      ScoreDTO s = uniqueScores.get(i);
      tg.putString(2, y++, (i + 1) + ". " + s.getPlayerName() + " - " + s.getGold());
    }

    showPlayerStat(tg, y);

    tg.putString(2, y + 15, "Press Enter to continue...");

    screen.refresh();

    // ждём нажатия Enter
    while (true) {
      KeyStroke key = screen.readInput();
      if (key != null && key.getKeyType() == KeyType.Enter) {
        break;
      }
    }

    // очищаем экран после выхода
    screen.clear();
    screen.refresh();
  }



  public void showPlayerStat(TextGraphics tg, int y) {
    tg.putString(2, y + 2, game.getPlayer().getDisplayName());
    tg.putString(2, y + 4, String.format("Gold: %d\n", game.getPlayer().getGold()));
    tg.putString(2, y + 5, String.format("Level: %d\n", game.getLevel().getLevel()));
    tg.putString(2, y + 6, String.format("Elixir used: %d\n", game.getPlayer().getElixirUse()));
    tg.putString(2, y + 7, String.format("Food eaten: %d\n", game.getPlayer().getFoodEaten()));
    tg.putString(2, y + 8, String.format("Kills: %d\n", game.getPlayer().getMonsterKills()));
    tg.putString(2, y + 9, String.format("Steps: %d\n", game.getPlayer().getCellMove()));
    tg.putString(2, y + 10, String.format("Hits: %d\n", game.getPlayer().getPlayerHits()));
    tg.putString(2, y + 11, String.format("Misses: %d\n", game.getPlayer().getPlayerMisses()));
  }
}
