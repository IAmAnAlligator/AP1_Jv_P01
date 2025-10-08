package Datalayer.DTO;

public class ScoreDTO {
  private String playerName;
  private int gold;
  private int level;
  private int elixirUse;
  private int foodEaten;
  private int monsterKills;
  private int cellMove;
  private int playerHits;
  private int playerMisses;

  public ScoreDTO(String playerName, int gold, int level, int elixirUse, int foodEaten,
      int monsterKills, int cellMove, int playerHits, int playerMisses) {
    this.playerName = playerName;
    this.gold = gold;
    this.level = level;
    this.elixirUse = elixirUse;
    this.foodEaten = foodEaten;
    this.monsterKills = monsterKills;
    this.cellMove = cellMove;
    this.playerHits = playerHits;
    this.playerMisses = playerMisses;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public int getGold() {
    return gold;
  }

  public void setGold(int gold) {
    this.gold = gold;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getElixirUse() {
    return elixirUse;
  }

  public void setElixirUse(int elixirUse) {
    this.elixirUse = elixirUse;
  }

  public int getFoodEaten() {
    return foodEaten;
  }

  public void setFoodEaten(int foodEaten) {
    this.foodEaten = foodEaten;
  }

  public int getMonsterKills() {
    return monsterKills;
  }

  public void setMonsterKills(int monsterKills) {
    this.monsterKills = monsterKills;
  }

  public int getCellMove() {
    return cellMove;
  }

  public void setCellMove(int cellMove) {
    this.cellMove = cellMove;
  }

  public int getPlayerHits() {
    return playerHits;
  }

  public void setPlayerHits(int playerHits) {
    this.playerHits = playerHits;
  }

  public int getPlayerMisses() {
    return playerMisses;
  }

  public void setPlayerMisses(int playerMisses) {
    this.playerMisses = playerMisses;
  }
}
