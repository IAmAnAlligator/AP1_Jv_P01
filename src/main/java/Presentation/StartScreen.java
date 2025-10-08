package Presentation;

import Domain.GameSession.GameFacadeInterface;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;

public class StartScreen {

  private final Screen screen;
  private boolean isStartScreenExit = false;
  private boolean isNewGame = false;
  private final GameFacadeInterface game;

  public StartScreen(Screen screen, GameFacadeInterface game) {
    this.screen = screen;
    this.game = game;
  }

  public void showStartScreen() {
    try {
      screen.clear();
      TextGraphics graphics = screen.newTextGraphics();

      TerminalSize terminalSize = screen.getTerminalSize();
      int screenWidth = terminalSize.getColumns();
      int screenHeight = terminalSize.getRows();
      int centerX = screenWidth / 2;

      graphics.setBackgroundColor(TextColor.ANSI.BLACK);
      graphics.fill(' ');

      drawDoubleBorder(graphics, 0, 0, screenWidth, screenHeight, TextColor.ANSI.YELLOW);

      int currentY = 3;
      graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
      centerText(graphics, "ROGUE: The Adventure Game", currentY++, centerX);
      currentY++;

      // Меню выбора
      String[] menuItems = {"New Game", "Continue", "Exit"};
      int selected = 0;

      boolean menuActive = true;
      while (menuActive) {
        // рисуем меню
        for (int i = 0; i < menuItems.length; i++) {
          if (i == selected) {
            graphics.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
            centerText(graphics, menuItems[i], currentY + i, centerX);
          } else {
            graphics.setForegroundColor(TextColor.ANSI.WHITE);
            centerText(graphics, menuItems[i], currentY + i, centerX);
          }
        }
        screen.refresh();

        KeyStroke key = screen.readInput();
        if (key == null) {
          continue;
        }

        if (key.getKeyType() == KeyType.ArrowUp) {
          selected = (selected - 1 + menuItems.length) % menuItems.length;
        } else if (key.getKeyType() == KeyType.ArrowDown) {
          selected = (selected + 1) % menuItems.length;
        } else if (key.getKeyType() == KeyType.Enter) {
          switch (selected) {
            case 0 -> { // New Game
              isNewGame = true;
              readPlayerName(graphics, centerX - 10, currentY + menuItems.length + 2, 20);
            }
            case 1 -> { // Continue
              isNewGame = false;
            }
            case 2 -> { // Exit
              isNewGame = true;
              isStartScreenExit = true;
            }
          }
          menuActive = false;
        }
      }

      screen.setCursorPosition(null);
      screen.refresh();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void drawDoubleBorder(TextGraphics graphics, int startX, int startY, int width,
      int height, TextColor color) {
    graphics.setForegroundColor(color);
    graphics.setCharacter(startX, startY, '╔');
    graphics.setCharacter(startX + width - 1, startY, '╗');
    graphics.setCharacter(startX, startY + height - 1, '╚');
    graphics.setCharacter(startX + width - 1, startY + height - 1, '╝');
    for (int x = startX + 1; x < startX + width - 1; x++) {
      graphics.setCharacter(x, startY, '═');
      graphics.setCharacter(x, startY + height - 1, '═');
    }
    for (int y = startY + 1; y < startY + height - 1; y++) {
      graphics.setCharacter(startX, y, '║');
      graphics.setCharacter(startX + width - 1, y, '║');
    }
  }

  private void centerText(TextGraphics graphics, String text, int y, int centerX) {
    int x = centerX - text.length() / 2;
    graphics.putString(x, y, text);
  }

  private void readPlayerName(TextGraphics graphics, int startX, int startY, int boxWidth)
      throws IOException {
    // Показываем надпись
    graphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
    graphics.putString(startX, startY - 1, "Enter your name:");

    StringBuilder nameBuilder = new StringBuilder();
    boolean reading = true;

    while (reading) {
      // Обновляем поле ввода
      updateInputDisplay(graphics, startX, startY, nameBuilder.toString(), boxWidth);

      KeyStroke key = screen.readInput();
      if (key != null) {
        if (key.getKeyType() == KeyType.Character) {
          if (nameBuilder.length() < 12) {
            nameBuilder.append(key.getCharacter());
          }
        } else if (key.getKeyType() == KeyType.Enter) {
          game.getPlayer().setDisplayName(nameBuilder.toString().trim());
          if (!game.getPlayer().getDisplayName().isEmpty()) {
            reading = false;
          }
        } else if (key.getKeyType() == KeyType.Backspace) {
          if (!nameBuilder.isEmpty()) {
            nameBuilder.setLength(nameBuilder.length() - 1);
          }
        }
      }
    }

    // Скрываем курсор после ввода
    screen.setCursorPosition(null);
    screen.refresh();
  }

  private void updateInputDisplay(TextGraphics graphics, int startX, int startY, String text,
      int boxWidth) throws IOException {
    graphics.setBackgroundColor(TextColor.ANSI.BLACK);
    graphics.setForegroundColor(TextColor.ANSI.WHITE);

    // очищаем строку ввода
    for (int i = 0; i < boxWidth; i++) {
      graphics.setCharacter(startX + i, startY, ' ');
    }

    // выводим текст с "_"
    graphics.putString(startX, startY, text + "_");

    // курсор на позиции "_"
    screen.setCursorPosition(
        new com.googlecode.lanterna.TerminalPosition(startX + text.length(), startY));
    screen.refresh();
  }

  public boolean isStartScreenExit() {
    return !isStartScreenExit;
  }

  public boolean isNewGame() {
    return isNewGame;
  }
}
