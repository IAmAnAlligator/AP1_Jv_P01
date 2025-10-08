package Presentation;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import Domain.GameSession.GameFacade;
import Domain.GameSession.GameFacadeInterface;
import java.awt.*;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Main {
  private static final Logger logger = Logger.getLogger(Main.class.getName());

  public static void main(String[] args) {
    try {
      // начальный размер окна (100x35 символов)
      TerminalSize terminalSize = new TerminalSize(100, 35);

      // кастомный шрифт
      Font font = new Font("Monospaced", Font.PLAIN, 24);
      SwingTerminalFontConfiguration fontConfig =
          SwingTerminalFontConfiguration.newInstance(font);

      DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
          .setInitialTerminalSize(terminalSize)
          .setTerminalEmulatorFontConfiguration(fontConfig);

      SwingTerminalFrame terminal = (SwingTerminalFrame) terminalFactory.createTerminal();

      // разрешаем изменение размера окна
      terminal.setResizable(true);

      // разворачиваем на весь экран
      terminal.setExtendedState(JFrame.MAXIMIZED_BOTH);

      Screen screen = new com.googlecode.lanterna.screen.TerminalScreen(terminal);
      screen.startScreen();
      // Скрываем курсор
      screen.setCursorPosition(null);

      GameFacadeInterface game = new GameFacade();

      Controller controller = new Controller(screen, game);
      controller.startGame();
      screen.stopScreen();

    } catch (Exception e) {
      logger.info("Error in Main");
    }
  }
}
