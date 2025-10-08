package Datalayer;

import Datalayer.DTO.ScoreDTO;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreDataLayer {
  private static final String SCORE_FILE = "scores.json";
  private static final Gson gson = new Gson();

  public static List<ScoreDTO> loadScores() {
    try (FileReader reader = new FileReader(SCORE_FILE)) {
      return gson.fromJson(reader, new TypeToken<List<ScoreDTO>>() {}.getType());
    } catch (IOException e) {
      return new ArrayList<>();
    }
  }

  public static void saveScores(List<ScoreDTO> scores) {
    try (FileWriter writer = new FileWriter(SCORE_FILE)) {
      gson.toJson(scores, writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void addScore(ScoreDTO score) {
    List<ScoreDTO> scores = loadScores();
    scores.add(score);
    saveScores(scores);
  }
}

