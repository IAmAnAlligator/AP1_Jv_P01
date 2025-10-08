package Datalayer;

import Domain.GameSession.Game;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Datalayer.DTO.GameDTO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class DataLayer {
    private static final String FILE_PATH = "data.json";
    private static final String FILE_STAT = "stat.txt";
    private static final Logger logger = Logger.getLogger(DataLayer.class.getName());

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    public DataLayer() {}
    public static void save(GameDTO gameDTO) {
        try (FileWriter writer = new FileWriter(FILE_PATH, false)) {
            gson.toJson(gameDTO, writer);
            logger.info("Game data saved successfully to " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error saving game data: " + e.getMessage());
        }
    }

    public static GameDTO load() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            logger.info("Game data loaded successfully from " + FILE_PATH);
            return gson.fromJson(reader, GameDTO.class);
        } catch (IOException e) {
            System.err.println("Error loading game data: " + e.getMessage());
            return null;
        }
    }

    public void writeRecordStatToTxt(Game game) {
        try (FileWriter writer = new FileWriter(FILE_STAT, true)) {
            writer.write(
                    String.format("Level: %d, Gold: %d, Pass cell: %d, Monster KILL: %d\n", game.getLevel().getLevel(),
                            game.getPlayer().getGold(), game.getPlayer().getCellMove(), game.getPlayer().getMonsterKills()));
        } catch (IOException ex) {
            System.err.println("Файл не найден или ошибка чтения!");
        }
    }
}